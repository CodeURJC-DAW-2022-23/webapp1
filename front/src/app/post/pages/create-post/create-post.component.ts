import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TopicsService } from '../../services/topic.service';
import { Topic } from '../../interfaces/topic.interface';
import { PostService } from '../../services/post.service';
import { Post } from '../../interfaces/post.interface';


@Component({
  selector: 'app-post-form',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreatePostComponent implements OnInit {
  myForm!: FormGroup;

  topics: Topic[] = [];

  constructor(private postService: PostService, private topicsService: TopicsService, private router: Router, private formBuilder: FormBuilder) { }


  ngOnInit() {
    this.topicsService.getTopics().subscribe((topics: Topic[]) => {
      this.topics = topics;
    });

    this.myForm = this.formBuilder.group({
      'title': ['', Validators.required],
      'selectedTopics': this.formBuilder.control([]),
      'descriptionsAndImages': this.formBuilder.array([
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup()
      ])
    });
  }

  get selectedTopics(): string[] {
    return this.myForm.controls['selectedTopics'].value;
  }

  createDescriptionAndImageGroup(): FormGroup {
    return this.formBuilder.group({
      'description': [null],
      'image': [null]
    });
  }

  get descriptionsAndImagesControls() {
    return (this.myForm.get('descriptionsAndImages') as FormArray).controls;
  }

  loadFile(event: any, index: number) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();
      reader.onload = (e: any) => {
        (this.myForm.get('descriptionsAndImages') as FormArray).at(index).patchValue({ 'image': e.target.result });
      }
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  onSubmit() {
    if (this.myForm.valid) {
      const descriptionsAndImages = this.myForm.get('descriptionsAndImages')?.value.filter((item: any) => item.description !== null);
      const post: Post = {
        title: this.myForm.get('title')?.value,
        topicStrings: this.myForm.get('selectedTopics')?.value,
        items: descriptionsAndImages
      };

      this.postService.createPost(post).subscribe(
        (response) => {
          this.router.navigate(['/']);
        }
      );
    }
  }

}

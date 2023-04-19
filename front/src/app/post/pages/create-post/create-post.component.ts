import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { TopicsService } from '../../services/topic.service';
import { Topic } from '../../interfaces/topic.interface';



@Component({
  selector: 'app-post-form',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  myForm!: FormGroup;

  topics: Topic[] = [];
  selectedTopics: Topic[] = [];

  constructor(private topicsService: TopicsService, private router: Router, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.topicsService.getTopics().subscribe((topics: Topic[]) => {
      this.topics = topics;
    });
    this.myForm = this.formBuilder.group({
      'title': [null],
      'descriptionsAndImages': this.formBuilder.array([
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup(),
        this.createDescriptionAndImageGroup()
      ])
    });
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
    // Handle form submission logic here
    this.router.navigate(['/']);
  }
}

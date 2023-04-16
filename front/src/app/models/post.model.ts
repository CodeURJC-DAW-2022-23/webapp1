import { PostItem } from './postItem.model';
import { Topic } from './topic.model';
import { User } from './user.model';

export interface Post {
  id?: number;
  author: User;
  date: Date;
  title: string;
  upVotes: User[];
  downVotes: User[];
  numUpvotes: number;
  numDownvotes: number;
  votes: number;
  topics: Topic[];
  items: PostItem[];
  comments: Comment[];
}

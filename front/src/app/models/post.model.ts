import { PostItem } from './postItem.model';
import { User } from './user.model';

export interface Post {
  id?: number;
  authorName: string;
  date: Date;
  title: string;
  upVotes: User[];
  downVotes: User[];
  numUpvotes: number;
  numDownvotes: number;
  votes: number;
  topicNames: string[];
  items: PostItem[];
  comments: Comment[];
  followingAuthor: boolean;
}

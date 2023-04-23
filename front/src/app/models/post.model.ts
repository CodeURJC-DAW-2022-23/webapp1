import { PostItem } from './postItem.model';
import { User } from './user.model';

export interface Post {
  id: number;
  author: User;
  date: Date;
  title: string;
  upVotesUsernames: string[];
  downVotesUsernames: string[];
  numUpvotes: number;
  numDownvotes: number;
  votes: number;
  topicNames: string[];
  items: PostItem[];
  comments: Comment[];
  followingAuthor: boolean;
}

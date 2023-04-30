import { PostItem } from './postItem.model';

export interface Post {
  id: number;
  authorName: string;
  authorId: number;
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

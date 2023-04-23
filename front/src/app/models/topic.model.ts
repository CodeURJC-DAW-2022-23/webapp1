import { Post } from './post.model';

export interface Topic {
  id?: number;
  name: string;
  description: string;
  posts: Post[];
}

import { Post } from './post.model';
//import { UserRole } from './userRole.enum';

export interface User {
  id?: number;
  date: Date;
  username: string;
  password: string;
  email: string;
  role: string; //userRoleEnum
  enabled: boolean;
  locked: boolean;
  bio: string;
  imagePath: string;
  following: User[];
  followers: User[];
  posts: Post[];
  comments: Comment[];
}

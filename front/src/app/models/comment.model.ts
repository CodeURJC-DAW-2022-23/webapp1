import { User } from "./user.model";

export interface Comment {
  id?: number;
  author: User;
  date: Date;
  content: string;
  imagePath: string;
}

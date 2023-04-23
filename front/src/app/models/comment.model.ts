import { User } from "./user.model";

export interface Comment {
  id: number;
  authorName: string;
  date: Date;
  content: string;
  imagePath: string;
}

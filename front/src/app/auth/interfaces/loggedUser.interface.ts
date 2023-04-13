export interface LoggedUser {
  '@Id': number;
  id: number;
  date: Date;
  username: string;
  email: string;
  role: string;
  enabled: boolean;
  locked: boolean;
  bio: string;
  imagePath: string;
  posts: any[];
  comments: any[];
  admin: boolean;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  authorities: Authority[];
}

interface Authority {
  authority: string;
}

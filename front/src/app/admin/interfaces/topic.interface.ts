export interface Topic {
  '@Id': number;
  id: number;
  name: string;
  description: string;
  posts: any[];
  authorities: Authority[];
}

interface Authority {
  authority: string;
}
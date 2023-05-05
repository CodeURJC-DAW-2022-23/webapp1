export interface Follow {
  count: number;
  users: UserFollow[];
}

export interface UserFollow {
  username: string;
  id?: number;
  follow: boolean;
}

import { Message } from "./message";
import { User } from "./user";

export interface Room {
    id: number;
    name: string;
    users: User[];
    messages: Message[];
}

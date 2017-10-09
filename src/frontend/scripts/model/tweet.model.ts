import {Moment} from "moment";

export interface SKTweet {

    id: number;

    statusId: number;

    createdAt: Moment;

    text: string;

    authorName: string;

    authorImageUrl: string;

}
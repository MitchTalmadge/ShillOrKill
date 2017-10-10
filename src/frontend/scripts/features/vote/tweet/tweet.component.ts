import {Component, Input, OnInit} from '@angular/core';
import {SKTweet} from "../../../model/tweet.model";

@Component({
    selector: 'sk-tweet',
    templateUrl: 'tweet.component.html',
    styleUrls: ['tweet.component.scss']
})

export class TweetComponent implements OnInit {

    @Input()
    public tweet: SKTweet;

    constructor() {
    }

    ngOnInit() {
    }
}
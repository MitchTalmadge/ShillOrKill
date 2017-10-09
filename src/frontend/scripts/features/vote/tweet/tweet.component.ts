import {Component, Input, OnInit} from '@angular/core';
import {SKTweet} from "../../../model/tweet.model";

@Component({
    selector: 'sk-tweet',
    templateUrl: 'tweet.component.html',
    styleUrls: ['tweet.component.css']
})

export class TweetComponent implements OnInit {

    @Input()
    private tweet: SKTweet;

    constructor() {
    }

    ngOnInit() {
    }
}
import {Component, ContentChildren, Input, QueryList} from "@angular/core";
import {CardFooterComponent} from "./card-footer/card-footer.component";
import {CardHeaderComponent} from "./card-header/card-header.component";

@Component({
    selector: 'sk-card',
    templateUrl: 'card.component.html',
    styleUrls: ['card.component.css']
})
export class CardComponent {

    @ContentChildren(CardHeaderComponent) header: QueryList<CardHeaderComponent>;
    @ContentChildren(CardFooterComponent) footer: QueryList<CardFooterComponent>;

    @Input() title: string;

    @Input() noTopBorder: boolean = false;

}
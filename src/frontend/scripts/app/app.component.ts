import {Component, ViewEncapsulation} from "@angular/core";
import {AnalyticsService} from "../core/services/analytics.service";

@Component({
    selector: 'sk-app',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class AppComponent {

    // noinspection JSUnusedLocalSymbols
    /**
     *  Note: Don't remove the un-used import, as it initializes the service.
     */
    constructor(analyticsService: AnalyticsService) {
    }

}
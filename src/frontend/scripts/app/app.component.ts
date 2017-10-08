import {Component, ViewEncapsulation} from "@angular/core";
import {AnalyticsService} from "../core/services/analytics.service";
import moment = require("moment");

@Component({
    selector: 'sk-app',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class AppComponent {

    // noinspection JSUnusedLocalSymbols
    constructor(analyticsService: AnalyticsService) {
        // Note: Don't remove the un-used import, as it initializes the service.

        // Moment messages
        moment.updateLocale('en', {
            calendar: {
                lastDay: '[Yesterday at] LT',
                sameDay: '[Today at] LT',
                nextDay: '[Tomorrow at] LT',
                lastWeek: '[Last] dddd [at] LT',
                nextWeek: '[Next] dddd [at] LT',
                sameElse: 'MM/DD/YYYY [at] LT'
            }
        });
    }


}
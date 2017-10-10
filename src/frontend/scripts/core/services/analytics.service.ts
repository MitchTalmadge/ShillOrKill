import {Injectable} from "@angular/core";
import {NavigationEnd, Router} from "@angular/router";
import {SKAnalyticsEvent} from "../../shared/analytics/analytics-event.model";

declare const ga: Function;

@Injectable()
export class AnalyticsService {

    constructor(private router: Router) {
        // Automatically send pageview requests upon route changes
        this.router.events.distinctUntilChanged((previous: any, current: any) => {
            if (current instanceof NavigationEnd) {
                return previous.url === current.url;
            }
            return true;
        }).subscribe((x: any) => {
            ga('set', 'page', x.url);
            ga('send', 'pageview');
        });
    }

    /**
     * Sends an event to Google for tracking.
     * @param analyticsEvent The event data.
     */
    public static sendEvent(analyticsEvent: SKAnalyticsEvent): void {
        ga('send', 'event', analyticsEvent.category, analyticsEvent.action, analyticsEvent.label, analyticsEvent.value);
    }

}
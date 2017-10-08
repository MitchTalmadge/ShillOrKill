import {Directive, ElementRef, HostListener, Input} from "@angular/core";
import {ATAnalyticsEvent} from "./analytics-event.model";
import {EventManager} from "@angular/platform-browser";
import {AnalyticsService} from "../../core/services/analytics.service";

@Directive({selector: '[at-analytics]'})
export class AnalyticsDirective {

    @Input('at-analytics') analyticsEvent: ATAnalyticsEvent;

    constructor(private element: ElementRef,
                private eventManager: EventManager) {
    }

    @HostListener('click')
    private onClick() {
        AnalyticsService.sendEvent(this.analyticsEvent)
    }

}
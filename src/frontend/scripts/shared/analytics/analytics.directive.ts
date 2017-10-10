import {Directive, ElementRef, HostListener, Input} from "@angular/core";
import {SKAnalyticsEvent} from "./analytics-event.model";
import {EventManager} from "@angular/platform-browser";
import {AnalyticsService} from "../../core/services/analytics.service";

@Directive({selector: '[sk-analytics]'})
export class AnalyticsDirective {

    @Input('sk-analytics') analyticsEvent: SKAnalyticsEvent;

    constructor(private element: ElementRef,
                private eventManager: EventManager) {
    }

    @HostListener('click')
    private onClick() {
        AnalyticsService.sendEvent(this.analyticsEvent)
    }

}
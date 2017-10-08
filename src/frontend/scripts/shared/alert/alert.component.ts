import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Observable, Subscription} from "rxjs";
import {trigger, state, style, transition, animate} from "@angular/animations";

@Component({
    selector: 'alert',
    templateUrl: 'alert.component.html',
    animations: [
        trigger('alertDisplayed', [
            state('1', style({opacity: 1})),
            state('0', style({opacity: 0, margin: 0, padding: '0 15px'})),
            transition('1 => 0', animate('200ms')),
            transition('0 => 1', animate('200ms'))
        ])
    ]
})
export class AlertComponent {

    timerRunning: boolean = false;

    visibilityTimer: Subscription;

    @Input() displayed: boolean = false;

    @Input() message: string;

    /**
     * Determines the color of the alert. May be one of 'info', 'danger', 'warning', and 'success'.
     * Defaults to 'info'.
     */
    @Input() severity: string = "info";

    @Input() autoCloseDelay: number = 2000;

    @Output()
    onAutoClose: EventEmitter<any> = new EventEmitter();

    /**
     * Starts the autoClose timer if it is enabled
     */
    private startTimer() {
        this.timerRunning = true;
        this.visibilityTimer = Observable.timer(this.autoCloseDelay).subscribe(
            i => {
                this.onAutoClose.next();
                this.stopTimer();
            });
    }

    /**
     * Stops the running autoClose timer if it exists.
     */
    private stopTimer() {
        this.timerRunning = false;
        if (this.visibilityTimer != undefined) {
            this.visibilityTimer.unsubscribe();
            this.visibilityTimer = undefined;
        }
    }

    display(message?: string, autoClose: boolean = true) {
        if (message) {
            this.message = message;
        }

        if (this.message) {
            if (autoClose) {
                this.displayed = false;
                this.stopTimer();
                this.startTimer();
            } else {
                this.displayed = true;
            }
        }
    }

    isDisplayed(): boolean {
        return this.displayed || this.timerRunning;
    }

}
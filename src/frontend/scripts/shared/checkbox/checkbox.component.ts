import {Component, EventEmitter, forwardRef, Input, Output} from "@angular/core";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {noop} from "rxjs/util/noop";

@Component({
    selector: 'sk-checkbox',
    templateUrl: 'checkbox.component.html',
    styleUrls: ['./checkbox.component.css'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => CheckboxComponent),
            multi: true
        }
    ]
})
export class CheckboxComponent implements ControlValueAccessor {

    private innerValue: boolean = false;

    @Input() label: string;

    private changeListener: (value: boolean) => void = noop;

    @Output() valueChanged = new EventEmitter<boolean>();

    get value(): boolean {
        return this.innerValue;
    }

    set value(value: boolean) {
        this.innerValue = value;
        this.changeListener(value);
        this.valueChanged.emit(value);
    }

    writeValue(value: any): void {
        if (typeof value === "string")
            this.innerValue = value === "true";
        else
            this.innerValue = value;
    }

    registerOnChange(fn: any): void {
        this.changeListener = fn;
    }

    registerOnTouched(fn: any): void {
    }
}
export class Event {
    public id: string;
    public title: string;
    public city: string;
    public place: string;
    public date: string;

    constructor(obj: object) {
        Object.assign(this, obj);
    }
}

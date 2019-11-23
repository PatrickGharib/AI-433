package DataClass;

public class LabSlot extends Slot {

    public LabSlot(Day day, String startTime, int labMax, int labMin) {
        super(day, startTime, labMax, labMin);
        endCalc(this.startHour,this.startMin);
    }

    public LabSlot(String day, String startTime, int labMax, int labMin) {
        super(Day.valueOf(day), startTime, labMax, labMin);
        endCalc(this.startHour,this.startMin);
    }

    private void endCalc(int shour,int smin){
        if(this.day.equals("FR")){
            this.endHour = shour + 2;
            this.endMin = smin;
        }
        this.endHour = shour + 1;
        this.endMin = smin;
    }
}


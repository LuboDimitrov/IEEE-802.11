package main;

public class Main {

    public static final int stations = 43; //number of stations
    public static final int SIFS = 10; //SIFS in microseconds
    public static final int DIFS = 50; //DIFS in microseconds
    public static final int sigma = 20; //slot time in microseconds
    public static final int PHYH = 72 + 24; //Physical Header, in microseconds (see documentation)
    public static final double MACH = 272.0 / 11.0; //MAC Header in IEEE 802.11, in microseconds
    public static final double ACK = 112.0 / 11.0 + PHYH; //ACK duration, in microseconds: 112 bit at 11Mbps + Physical Header duration
    public static final double totalHeader = PHYH + MACH; //   equivalent duration of MAC header + PHY header

    public static void main(String[] args) {
        S(16384); //payload size in bits
    }

    //first equation (14); x is payload size in bits
    public static double Ts (int x){
        return (totalHeader + (double) x / 11 + SIFS + ACK + DIFS);
    }

    //second equation (14); x is payload size in bits
    public static double Tc (int x){
        return (totalHeader +  (double) x / 11 + DIFS);
    }

    //equation (28)
    public static double Thau (int x){
        return (1.0 / (stations * (Math.sqrt (Tc(x) / (2 * sigma)))));
    }

    //ptr equation (10)
    public static double Ptr (int x){
        return (1 - (Math.pow((1 - Thau(x)), stations)));
    }

    //ps equation (11)
    public static double Ps (int x){
        return (stations * Thau(x) * (Math.pow((1 - Thau(x)), stations - 1)) / Ptr(x));
    }

    public static void S(int x){
        double value = (((double) x / 11) / (Ts(x) - Tc(x) + (sigma * (1 - Ptr(x)) / Ptr(x) + Tc(x)) / Ps(x)));
        System.out.println("S: "+value);
    }
}

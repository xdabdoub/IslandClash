package me.yhamarsheh.islandclash.game.session;

import java.util.Arrays;

public class Layout {

    private int[] nLayout;

    public Layout(int[] nLayout) {
        this.nLayout = nLayout;
    }

    public Layout() {
        this.nLayout = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    public void setNLayout(int[] nLayout) {
        this.nLayout = nLayout;
    }

    public int[] getNLayout() {
        return nLayout;
    }

    public int[] fromString(String s) {
        String[] numbersStringArray = s.split(",");
        int[] numbersArray = new int[numbersStringArray.length];

        for (int i = 0; i < numbersStringArray.length; i++) {
            numbersArray[i] = Integer.parseInt(numbersStringArray[i]);
        }

        if (numbersArray.length < 9) {
            numbersArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        }

        return numbersArray;
    }

    @Override
    public String toString() {
        return String.join(",", Arrays.stream(nLayout)
                .mapToObj(String::valueOf)
                .toArray(String[]::new));
    }
}

package com.sinosoft;

import com.sinosoft.aod.feed.model.Monitor;
import org.junit.Test;

import java.text.DecimalFormat;

public class Demo {
    @Test
    public void  test() {
        int  dialNum=7;
        int successNum=3;
        Monitor monitor=new Monitor();
        DecimalFormat a = new DecimalFormat("#.00%");
        System.out.println(a.format((float)successNum / dialNum));
//        System.out.println(a.format(0.56845));

    }
}

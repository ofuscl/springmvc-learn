package learn.pdf.wkhtmltopdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by YScredit on 2018/4/26.
 */
public class HtmlToPdfInter extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(HtmlToPdfInter.class);

    private InputStream is;

    public HtmlToPdfInter(InputStream is) {
        this.is = is;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            br.readLine();
            System.out.println(br.readLine());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}

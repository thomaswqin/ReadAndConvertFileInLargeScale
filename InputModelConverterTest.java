package com.worksap.company.framework.magiccore.engine.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.worksap.company.framework.magiccore.configuration.MagicCoreConfigurations;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MagicCoreConfigurations.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InputModelConverterTest {
    String addressPrefix = "C:\\Users\\qin_d-PC\\Downloads\\MIC_download_models";
    String addressPrefix2 = "C:\\Users\\qin_d-PC\\Desktop\\MIC_input_models";

    // String fileName = "AM112471-Front page.inputmodel.json";

    @Test
    public void testConvertRequestIntoModel() throws IOException {
        File[] files = new File(addressPrefix).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }

            FileReader in = null;
            FileWriter out = null;
            Stack<Character> s = new Stack();

            try {
                in = new FileReader(file);
                out = new FileWriter(addressPrefix2 + "\\" + file.getName());

                int c;
                while ((c = in.read()) != -1) {
                    char curChar = (char)c;
                    if (curChar == '{') {
                        s.push('}');
                    } else if (!s.isEmpty() && s.peek().equals(curChar)) {
                        s.pop();
                    }

                    if (s.size() >= 2) {
                        System.out.print(curChar);
                        out.write(c);
                    }
                }

                System.out.println('}');
                out.write('}');
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (Objects.nonNull(in)) {
                    in.close();
                }
                if (Objects.nonNull(out)) {
                    out.close();
                }
            }
        }
    }
}

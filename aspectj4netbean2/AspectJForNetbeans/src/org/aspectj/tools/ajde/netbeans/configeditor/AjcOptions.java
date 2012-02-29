/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aspectj.tools.ajde.netbeans.configeditor;

import java.util.List;

/**
 *
 * @author Ramos
 */
public class AjcOptions {

    private AjcOption ONE_DOT_FIVE_OPTION;
    private AjcOption INCREMENTAL;
    private AjcOption SOURCEROOTS;
    private AjcOption ASPECT_PATH;
    private AjcOption INPATH;
//    private static HashMap<String, AjcOption> optionsMap = new HashMap<String, AjcOption>();

//    static {
//        optionsMap.put(ONE_DOT_FIVE_OPTION.getSwitchS(), ONE_DOT_FIVE_OPTION);
//        optionsMap.put(INCREMENTAL.getSwitchS(), INCREMENTAL);
//    }
    private static AjcOptions INSTANCE = new AjcOptions();
    private boolean isSourceRoots = false;

    public boolean isIsSourceRoots() {
        return isSourceRoots;
    }

    private AjcOptions() {

    }

    static AjcOptions getDefault() {
        return INSTANCE;
    }
    

    AjcOption[] parseOptions(List<String> entries) {
//        System.out.println("parseOptions");
        createOptions();
        
        int i = 0;
        while (i < entries.size()) {
            //for (int i = 0; i < entries.size(); i++) {
            String string = extractEntry(entries.get(i));
//            System.out.println("parsing (" + i + ") " + string);
            if (string.equals("-sourceroots")){
                i++;
                if (i >= entries.size()) {
                    break;
                }
                String wert = extractEntry(entries.get(i));
                SOURCEROOTS.setSet(true).setValue(wert);
                isSourceRoots = true;
//                System.out.println("IS SOURCE ROOTS ");
            }
            else if (string.equals("-1.5")) {
                ONE_DOT_FIVE_OPTION.setSet(true);
//                System.out.println("was ONE_DOT_FIVE_OPTION");
//            } else if (string.equals("-incremental")) {
//                INCREMENTAL.setSet(true);
//                System.out.println("was INCREMENTAL");
            } else if (string.equals("-aspectpath")) {
                //get next entry
                i++;
                if (i >= entries.size()) {
                    break;
                }
                String wert = extractEntry(entries.get(i));
                ASPECT_PATH.setSet(true).setValue(wert);
//                System.out.println("was ASPECT_PATH");
            } else if (string.equals("-inpath")) {
                //get next entry
                i = i++;
                if (i >= entries.size()) {
                    break;
                }
                String wert = extractEntry(entries.get(i));
                INPATH.setSet(true).setValue(wert);
//                System.out.println("was INPATH");
            }
            i++;
        }
        if (isSourceRoots) return new AjcOption[]{SOURCEROOTS, ONE_DOT_FIVE_OPTION, ASPECT_PATH, INPATH, INCREMENTAL};
        else return new AjcOption[]{ONE_DOT_FIVE_OPTION, ASPECT_PATH, INPATH};
    }

    private void createOptions() {
        isSourceRoots = false;
        SOURCEROOTS = new AjcOption("-sourceroots", "",true);
        SOURCEROOTS.setHidden(true);
        ONE_DOT_FIVE_OPTION = new AjcOption("-1.5", "Compatibility Level 1.5");
        INCREMENTAL = new AjcOption("-incremental", "Incremental");
        ASPECT_PATH = new AjcOption("-aspectpath", "Aspect Path", true);
        INPATH = new AjcOption("-inpath", "In Path", true);
    }

    private String extractEntry(String text) {
        return text.substring("unrecognized option: ".length());
    }

    static class AjcOption {

        private String switchS;//-1.5
        private String label;//level 1.5
        private String value;//level 1.5
        private boolean text = false;

        public boolean isText() {
            return text;
        }

        public AjcOption setSet(boolean set) {
            this.set = set;
            return this;
        }

        public AjcOption setValue(String value) {
            this.value = value;
//            System.out.println("value: "+value);
            if (value == null || value.trim().length() == 0){
                set = false;
            }else {
                set = true;
            }
            return this;
        }
        private boolean set = false;
        private boolean hidden = false;

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }
        public boolean isSet() {
            return set;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public String getSwitchS() {
            return switchS;
        }

        public AjcOption(String switchS, String label) {
            this(switchS, label, false, false, null);
        }

        public AjcOption(String switchS, String label, boolean hasText) {
            this(switchS, label, false, hasText, null);
        }

        public AjcOption(String switchS, String label, boolean set, boolean hasText, String value) {
            this.switchS = switchS;
            this.label = label;
            this.set = set;
            this.value = value;
            this.text = hasText;
        }
//    public static Collection<AjcOption> getOptions() {
//        return optionsMap.values();
//    }
    /*
         * injars
         * inpath
         * outjar
         * outxml
         * incremental
         * sourceroots
         * crossrefs
         * emacssym
         * Xlint
         * classpath
         * bootclasspath
         * extdirs
         * target
         * 1.3
         * 1.4
         * 1.5
         * source
         * nowarn
         * warn
         * deprecation
         * noImportError
         * proceedOnError
         * showWeaveInfo
         * encoding
         * 
         * 
         */
    }
}

//package sample;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//class ApplicantFileManager {
//
//    ApplicantFileManager() {
//    }
//
//    private String fileToString(String filename) {
//        try {
//            File file = new File("./phase2/" + filename);
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String resume = "";
//            String line = br.readLine();
//            while (line != null) {
//                resume += line + "\n";
//                line = br.readLine();
//            }
//            return resume;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    String fileMaker(String type) {
//        String fileName = Main.getInput("Enter " + type + " filename. (Eg: phase1/" + "johnsmithresume.txt" +
//                "If the file does not exist, press enter again to type in your resume.");
//        if (fileName.equals("")) {
//            return Main.getInput("Please type out your resume(if you wish to go to a new line type in \\n)" +
//                    "and press enter once you're done.");
//        } else {
//            String file = fileToString(fileName);
//            if (file == null) {
//                return fileMaker(type);
//            } else {
//                return file;
//            }
//        }
//    }
//}

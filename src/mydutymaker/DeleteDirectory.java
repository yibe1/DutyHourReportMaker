/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydutymaker;

/**
 *
 * @author Jimma University
 */
import java.io.File;
 
class DeleteDirectory {
 
    // function to delete subdirectories and files
    public void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        // inside directory
        for (File subfile : file.listFiles()) {
 
            // if it is a subfolder,e.g Rohan and Ritik,
            //  recursively call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
 
            // delete files and empty subfolders
            subfile.delete();
        }
    }
 
    public void main(String filepath)
    {
        // store file path
        File file = new File(filepath);
 
        // call deleteDirectory function to delete
        // subdirectory and files
        deleteDirectory(file);
 
        // delete main GFG folder
        file.delete();
    }
}
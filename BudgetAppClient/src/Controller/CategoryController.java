/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import communication.Communication;
import coordinator.MainCoordinator;
import domain.Category;
import frm.FrmCategory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mia Pecelj
 */
public class CategoryController {
    private final FrmCategory frmCategory;

    public CategoryController(FrmCategory frmCategory) {
        this.frmCategory = frmCategory;
        addActionListener();
    }
     private void addActionListener() {
        frmCategory.SaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Category category = new Category();
                String name = frmCategory.getInputName().getText().trim();
                if(name.isEmpty()){
                    frmCategory.getLblError().setText("Enter a name");
                }
                category.setName(name);
                try {
                    Communication.getInstance().saveCategory(category);
                    JOptionPane.showMessageDialog(frmCategory, "Category saved");
                    frmCategory.dispose();  
                } catch (Exception ex) {
                    Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmCategory, "Category exists");
                    
                }
                 
            }
            
        });
        frmCategory.CancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frmCategory.dispose();
                   
            }

        });
    }
     public void openForm(){
          frmCategory.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
         frmCategory.setVisible(true);
     }
    
    
    
}

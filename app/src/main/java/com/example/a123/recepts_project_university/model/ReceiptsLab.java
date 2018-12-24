package com.example.a123.recepts_project_university.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.db.model.StepToReceipts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReceiptsLab {
    private static final String TAG = "ImagePath";
    private static final String Image_Folder_Icon = "icon";
    private static final String Ingredienti = "ingredienti";
    private static final String Opisanie = "opisanie";
    private static final String Sam_Recept = "sam_recept";

    private AssetManager mAssetManager;
    private List<Receipt> mReceipts;
    private static ReceiptsLab sReceiptsLab;
    private int step;
    private String[] titles;
    private String[] icon_name;
    private String[] list_of_descriptions;
    private List<String> mySteps = new ArrayList<>();
    private List<StepToReceipts> mStepToReceiptsList = new ArrayList<>();

    public Receipt getReceipt(int position){
        return mReceipts.get(position);
    }

    public List<Receipt> getReceipts() {
        return mReceipts;
    }

    public static ReceiptsLab get(Context context) {
        if (sReceiptsLab == null) {
            sReceiptsLab = new ReceiptsLab(context);
        }
        return sReceiptsLab;
    }

    private ReceiptsLab(Context context) {
        mReceipts = new ArrayList<>();

        mAssetManager = context.getAssets();
        try {
            list_of_descriptions = mAssetManager.list(Opisanie);
        }catch (IOException exc){
            Log.i("IOException", exc.getMessage());
        }
        titles = context.getResources().getStringArray(R.array.spisok_nazvaniy);
        icon_name = context.getResources().getStringArray(R.array.icon_name);
        for (int i = 0; i < icon_name.length; i++) {
            Receipt receipt = new Receipt();
            receipt.setImageMain(getInformationFormText(icon_name[i]));
            receipt.setTitle(titles[i]);
            receipt.setDescription(getInformationFormText(Opisanie+"/"+list_of_descriptions[i]));
            receipt.setIngredients(getInformationFormText(Ingredienti+"/"+"ing"+i+".txt"));
            receipt.setIcon(icon_name[i]);
            String s = getInformationFormText(Sam_Recept+"/recept"+i+".txt");
            MyReceptsSteps(StrString(s),receipt.getId_receipts());
            receipt.setListStep(mStepToReceiptsList);
            mReceipts.add(receipt);
        }

    }

    private void saveToBase(){
        AppDbHelper appDbHelper = TakeDb.getAppDbHelper();
        for(int j = 0;j<mReceipts.size();j++) {
            Long key = appDbHelper.saveReceipts(mReceipts.get(j));
            for (int i = 0; i < mReceipts.get(j).getListStep().size(); i++) {
                mReceipts.get(j).getListStep().get(i).setId_receipts(key);
                appDbHelper.saveSteps(mReceipts.get(j).getListStep().get(i));
            }
        }
    }

    public List<Receipt> getReceipts(String query){
        List<Receipt> receiptList = new ArrayList<>();
        for (Receipt receipt:
                mReceipts) {
            if(receipt.getTitle().toLowerCase().contains(query.toLowerCase())){
                receiptList.add(receipt);
            }
        }
        return receiptList;
    }

    public void MyReceptsSteps(List<String> Steps, Long id){
        mySteps = new ArrayList<>();
        mStepToReceiptsList = new ArrayList<>();
        step = 1;

        for (String s : Steps) {
            mySteps.addAll(Arrays.asList(s.split("``")));
        }

        for (int i = 0;i<mySteps.size();i++){
            if(mySteps.get(i).contains("Шаг")){
                mySteps.set(i+2,mySteps.get(i)+"\n"+mySteps.get(i+2));
                StepToReceipts stepToReceipts = new StepToReceipts();
                stepToReceipts.setImageDesciption(mySteps.get(i+2));
                stepToReceipts.setImageToStep(mySteps.get(i+1));
                stepToReceipts.setStep(step);
                mStepToReceiptsList.add(stepToReceipts);
                i+=2;
            }else{
                if(mySteps.size()-2>i){
                    StepToReceipts stepToReceipts = new StepToReceipts();
                    stepToReceipts.setImageDesciption(mySteps.get(i+1));
                    stepToReceipts.setImageToStep(mySteps.get(i));
                    stepToReceipts.setStep(step);
                    mStepToReceiptsList.add(stepToReceipts);
                    i+=1;
                }
            }
        }
    }

    private String getInformationFormText(String filename){
        StringBuilder text = new StringBuilder("");
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mAssetManager.open(filename));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            while((s = bufferedReader.readLine())!=null){
                text.append(s);
            }
        }catch(IOException ex){
            Log.i("IOException",ex.getMessage());
        }
        return text.toString();
    }

    private void loadImage() {
        String[] ImageNames = null;
        try {
            ImageNames = mAssetManager.list(Image_Folder_Icon);
            for (int i = 0; i < icon_name.length; i++) {

            }
        } catch (IOException ioe) {
            Log.e(TAG, "Не найдено", ioe);
        }
    }

    private List<String> StrString(String str){
        step = 0;
        List<String> map = new ArrayList<>();
        for (String s: str.split("Шаг \\d")) {
            map.add("Шаг "+step+" "+s);
            step++;
        }
        map.remove(0);
        return map;
    }
}

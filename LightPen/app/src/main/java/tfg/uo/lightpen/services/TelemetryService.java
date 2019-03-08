package tfg.uo.lightpen.services;

import android.app.ActivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.TelemetryInfo;
import tfg.uo.lightpen.model.ContextData;

import static android.content.Context.ACTIVITY_SERVICE;

public class TelemetryService {

    private static TelemetryThread telemetryThread = new TelemetryThread();

    private static ArrayList<String> cpu= new ArrayList<String>();

    private static ContextData ctx;
    private static List<ActivityManager.RunningAppProcessInfo> processes;
    private static ActivityManager mgr;

    private static final int delayMilis = 100;

    public static ArrayList<Error> cancel(){
        telemetryThread.close();
        ArrayList<Error> errors = new ArrayList<>();
        for(String tel : cpu){
            errors.add(new TelemetryInfo(tel));
        }
        ctx = null;
        cpu = new ArrayList<>();
        return errors;
    }

    public static void initialize(ContextData context){
        if(!telemetryThread.ismRunning()) {
            cpu = new ArrayList<>();
            ctx = context;

            mgr = (ActivityManager)ctx.getContext().getSystemService(ACTIVITY_SERVICE);
            processes = mgr.getRunningAppProcesses();

            AsyncTask.execute(telemetryThread);
        }
    }

    private static String logOperations(){
        for(Iterator i = processes.iterator(); i.hasNext(); )
        {
            ActivityManager.RunningAppProcessInfo p = (ActivityManager.RunningAppProcessInfo)i.next();
            /*
            Log.e("DEBUG", "  process name: "+p.processName);
            Log.e("DEBUG", "     pid: "+p.pid);
            int[] pids = new int[1];
            pids[0] = p.pid;
            android.os.Debug.MemoryInfo[] MI = mgr.getProcessMemoryInfo(pids);
            Log.e("memory","     dalvik private: " + MI[0].dalvikPrivateDirty);
            Log.e("memory","     dalvik shared: " + MI[0].dalvikSharedDirty);
            Log.e("memory","     dalvik pss: " + MI[0].dalvikPss);
            Log.e("memory","     native private: " + MI[0].nativePrivateDirty);
            Log.e("memory","     native shared: " + MI[0].nativeSharedDirty);
            Log.e("memory","     native pss: " + MI[0].nativePss);
            Log.e("memory","     other private: " + MI[0].otherPrivateDirty);
            Log.e("memory","     other shared: " + MI[0].otherSharedDirty);
            Log.e("memory","     other pss: " + MI[0].otherPss);

            Log.e("memory","     total private dirty memory (KB): " + MI[0].getTotalPrivateDirty());
            Log.e("memory","     total shared (KB): " + MI[0].getTotalSharedDirty());
            Log.e("memory","     total pss: " + MI[0].getTotalPss());
            Log.e("CPU", "     cpu " + getCPUPer(p.pid));
            */
            cpu.add(getCPUPer(p.pid));
        }
        return "";
    }

    private static String getCPUPer(int pid){
        String cpuPer = "";
        try {
            String[] cmd = {"ps", "-p", ""+pid, "-o", "etime=,%cpu=,%mem=;"};
            //String[] cmd = {"ps", "-p", ""+pid};
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                if(s != "" && s.contains(":")){
                    String[] string = s.split(" ");
                    cpuPer = string[6]+","+string[7]+","+string[8]+";";
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            cpuPer = "";
        }
        return cpuPer;
    }

    private static float getCpuPer(String procesName) { //for single process

        float cpuPer = 0;
        try {

            String[] cmd = {"top", "-n", "1"};
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                if (s.contains(procesName)) {
                    String [] arr = s.split(" ");
                    //for (int i = 0; i < arr.length; i++) {
                        if(arr.length > 16 && arr[16] != "")
                            cpuPer = Float.parseFloat(arr[16]);
                            //if (arr[i].contains("%")) {
                            //   s = arr[i].replace("%", "");
                            //  cpuPer = Float.parseFloat(s);
                        break;

                    //}
                    //System.out.println(s);
                }
            }

            // read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            //while ((s = stdError.readLine()) != null) {
            //System.out.println(s);
            //}
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            cpuPer = 0;
        }
        return cpuPer;
    }

    public static class TelemetryThread extends Thread {


        private boolean mRunning = false;

        @Override
        public void run() {
            mRunning = true;
            while (mRunning) {
                try {
                    logOperations();
                    Thread.sleep(delayMilis);
                }catch(Exception ex){}
            }
        }

        public void close() {
            mRunning = false;
        }

        public boolean ismRunning(){
            return mRunning;
        }
    }
}

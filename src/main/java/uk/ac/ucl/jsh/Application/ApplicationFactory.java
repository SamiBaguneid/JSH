package uk.ac.ucl.jsh.Application;

public class ApplicationFactory {
    public Application getInstance(boolean unsafe, String appName) throws Exception{
        Application app = getApp(appName);
        if(unsafe) {
            return new Unsafe(app);
        }else{
            return app;
        }
    }


    public Application getApp(String appName) throws Exception{
        switch (appName) {
            case "cd":
                return new Cd();
            case "pwd":
                return new Pwd();
            case "ls":
                return new Ls();
            case "cat":
                return new Cat();
            case "echo":
                return new Echo();
            case "head":
                return new Head();
            case "tail":
                return new Tail();
            case "grep":
                return new Grep();
            case "wc":
                return new Wc();
            case "sed":
                return new Sed();
            case "find":
                return new Find();
            case "mv":
                return new Mv();
            default:
                throw new Exception(appName + ": unknown application");
        }
    }
}

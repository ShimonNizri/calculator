import java.util.Arrays;

public class Calculator {
    public enum MessageType {DIVISION_BY_ZERO, Syntax_Error, UNDEFINED_OPERATION}
    public static String wayToSolution;
    private static boolean Once;
    public static double getSolving(String exercise) throws Exception{
        try {
            Once = true;
            System.out.println(Arrays.toString(order("(" + exercise + ")")));
            return solveAnExercise(order("(" + exercise + ")"));
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public static String getWayToSolution(){
        return wayToSolution;
    }
    private static double PlusAndMinus(String[] exercise) throws Exception {
        double sum = 0;
        boolean saveMethod = exercise.length > 2;
        for (int i = 0; i < exercise.length-1; i+=2) {
            try {
                sum += Double.parseDouble(exercise[i + 1]) * (exercise[i].equals("-") ? -1 : 1);
            }catch (Exception e){
                throw new Exception(MessageType.Syntax_Error.toString());
            }
        }
        if (saveMethod)
            wayToSolution = wayToSolution + "<br> <font color='blue'>" + String.join("",exercise)  +"</font>" + " = <font color='green'>" + sum + "</font>";
        return sum;
    }
    private static String[] multiplicationAndDivision (String[] exercise) throws Exception{
        for (int i = 2; i < exercise.length - 2; i++) {
            if (exercise[i].equals("*") || exercise[i].equals("/")) {
                double sum;
                if (exercise[i].equals("*")) {
                    sum = ((exercise[i - 2].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i - 1])) * ((exercise[i + 1].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i + 2]));
                } else {
                    if (((exercise[i + 1].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i + 2])) == 0)
                        throw new Exception(MessageType.DIVISION_BY_ZERO.toString());
                    sum = ((exercise[i - 2].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i - 1])) / ((exercise[i + 1].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i + 2]));
                }
                wayToSolution = wayToSolution + "<br> <font color='blue'>" + (exercise[i - 2]+exercise[i - 1]+exercise[i]+exercise[i +1]+exercise[i + 2])  +"</font>" + " = <font color='green'>" + sum + "</font>";
                String[] temp = new String[exercise.length - 3];
                exercise[i+1] = (sum < 0 ? "-" : "+");
                exercise[i+2] = Math.abs(sum) + "";
                int less = 0;
                for (int j = 0; j < exercise.length; j++) {
                    if (j >= (i - 2) && j <= i) {
                        less = 3;
                        continue;
                    }
                    temp[j - less] = exercise[j];
                }
                exercise = temp;
                i--;
            }
        }
        for (String exer : exercise) {
            if (exer.contains("*") || exer.contains("/"))
                throw new Exception(MessageType.Syntax_Error.toString());
        }
        try {
            return exercise;
        }catch (Exception e){
            throw new Exception(MessageType.Syntax_Error.toString());
        }
    }
    private static String[] order(String exercise) throws Exception{
        if (exercise.contains(" ") || exercise.contains("..")){
            throw new Exception(MessageType.Syntax_Error.toString());
        }
        while (exercise.contains("++") || exercise.contains("--") || exercise.contains("+-") || exercise.contains("-+")){
            exercise = exercise.replaceFirst("\\+\\+", "+");
            exercise = exercise.replaceFirst("--", "+");
            exercise = exercise.replaceFirst("-\\+", "-");
            exercise = exercise.replaceFirst("\\+-", "-");
        }
        for(int i = 1; i < exercise.length()-1; i++) {
            if (exercise.charAt(i) == '(' && (exercise.charAt(i-1) != '*' && exercise.charAt(i-1) != '+' && exercise.charAt(i-1) != '/' && exercise.charAt(i-1) != '-')
                    && (exercise.charAt(i-1) != '(') && (exercise.charAt(i-1) != '^') && (exercise.charAt(i-1) != '√')) {
                exercise = exercise.substring(0,i) + "*" + exercise.substring(i);
                i++;
            }
            if (exercise.charAt(i) == ')' && (exercise.charAt(i+1) != '*' && exercise.charAt(i+1) != '+' && exercise.charAt(i+1) != '/' && exercise.charAt(i+1) != '-')
                    && (exercise.charAt(i+1) != ')') && (exercise.charAt(i+1) != '^')) {
                exercise = exercise.substring(0,i+1) + "*" + exercise.substring(i+1);
                i++;
            }

        }
        //String[] tokens = exercise.split("(?=[+\\-*/()])|(?<=[+\\-*/()])");
        String[] tokens = exercise.split("(?=[+\\-*/()^√])|(?<=[+\\-*/()^√])");
        //if (tokens[0].matches("-?\\d+(\\.\\d+)?")){
          //  String[] temp = new String[tokens.length+1];
            //temp[0] = "+";
            //System.arraycopy(tokens, 0, temp, 1, tokens.length);
          //  tokens = temp;
        //}
        for (int i = 0 ; i < tokens.length-1;i++) {
            if ((tokens[i].equals("*") || tokens[i].equals("/") || tokens[i].equals("^") || tokens[i].equals("√") || tokens[i].equals("("))  && (tokens[i + 1].matches("-?\\d+(\\.\\d+)?"))){
                String[] temp = new String[tokens.length + 1];
                int less = 0;
                for (int j = 0; j < temp.length; j++) {
                    if (j == (i + 1)) {
                        temp[j] = "+";
                        less = 1;
                        continue;
                    }
                    temp[j] = tokens[j - less];
                }
                tokens = temp;
            }
        }
        if (Once) {
            for (int i = 2; i < tokens.length; i++) {
                if (tokens[i].equals("^") && tokens[i - 2].equals("-")) {
                    int index = i + 2;
                    if (tokens[i + 1].equals("(")) {
                        for (int k = i + 1; k < tokens.length; k++) {
                            if (tokens[k].equals(")")) {
                                index = k;
                                break;
                            }
                        }
                    }
                    String[] temp = new String[tokens.length + 1];
                    temp[index + 1] = ")";
                    int less = 0;
                    for (int n = 0; n < temp.length; n++) {
                        if (n == (index + 1)) {
                            less = 1;
                            continue;
                        }
                        temp[n] = tokens[n - less];
                    }
                    tokens = temp;

                    String[] temp1 = new String[tokens.length + 5];
                    temp1[i - 2] = "+";
                    temp1[i - 1] = "(";
                    temp1[i] = "-";
                    temp1[i + 1] = "1";
                    temp1[i + 2] = "*";
                    temp1[i + 3] = "+";
                    less = 0;
                    for (int j = 0; j < temp1.length; j++) {
                        if (j >= (i - 2) && j <= (i + 3)) {
                            less = 5;
                            continue;
                        }
                        temp1[j] = tokens[j - less];
                    }
                    tokens = temp1;
                }
            }
            Once = false;
        }
        try {
            return tokens;
        }catch (Exception e){
            throw new Exception(MessageType.Syntax_Error.toString());
        }
    }
    private static double solveAnExercise(String[] exercise) throws Exception{
        wayToSolution = "<html>";
        while (haveBrackets(exercise)) {
            String tempExercise;
            String temp1,temp2;
            double sum;
            int startIndex = 0;
            int endIndex = 0;
            tempExercise = String.join("", exercise);
            for (int i = 0; i < tempExercise.length(); i++) {
                if (tempExercise.charAt(i) ==  '(') {
                    startIndex = i + 1;
                }
                if (tempExercise.charAt(i) ==  ')') {
                    endIndex = i;
                    break;
                }
            }
            try {
                temp1 = tempExercise.substring(startIndex, endIndex);
            }catch (Exception e){
                throw new Exception(MessageType.Syntax_Error.toString());
            }
            wayToSolution = wayToSolution + "<br>"+tempExercise.substring(0,startIndex-1) + "<font color='orange'>(</font>" + temp1 +"<font color='orange'>)</font>" + tempExercise.substring(endIndex+1);
            //wayToSolution = wayToSolution + "\n"+tempExercise.substring(0,startIndex-1) + "\u001B[34m(\u001B[0m" + temp1 +"\u001B[34m)\u001B[0m" + tempExercise.substring(endIndex+1);
            //sum = PlusAndMinus(multiplicationAndDivision(Exponent(temp1.split("(?=[+\\-*/()^√])|(?<=[+\\-*/()^√])"))));
            //sum = PlusAndMinus(multiplicationAndDivision(Exponent(temp1.split("(?=[+\\-*/()^])|(?<=[+\\-*/()^])"))));
            sum = PlusAndMinus(multiplicationAndDivision(ExponentAndRoot(order(temp1))));
            //wayToSolution = wayToSolution + "<br> <font color='blue'>" + temp1  +"</font>" + " = <font color='green'>" + sum + "</font>";
            temp2 = "\\("+ temp1.replaceAll("([+\\-*/^√])", "\\\\$1") + "\\)";
            tempExercise = tempExercise.replaceFirst(temp2,(sum < 0 ? "":"+") +sum);
            //exercise = tempExercise.split("(?=[+\\-*/()^√])|(?<=[+\\-*/()^√])");
             exercise = order(tempExercise);
        }
        wayToSolution = wayToSolution + "</html>";
        try {
            if (exercise.length > 2)
                throw new Exception(MessageType.Syntax_Error.toString());
            return Double.parseDouble(exercise[1]) * (exercise[0].equals("+") ? 1 : -1);
        }catch (Exception e){
            throw new Exception(MessageType.Syntax_Error.toString());
        }
    }
    private static boolean haveBrackets(String[] exercise) {
        for (String s : exercise) {
            if (s.equals("(")) {
                return true;
            }
        }
        return false;
    }

    private static String[] ExponentAndRoot(String[] exercise) throws Exception{
        double sum ;
        String k;
        String temp1;
        k = String.join("", exercise);
        for (int i = 0; i < exercise.length-2; i++){
            if (exercise[i].equals("^") || exercise[i].equals("√")){
                if (exercise[i].equals("^")) {
                    sum = Math.pow((exercise[i - 2].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i - 1]), (exercise[i + 1].equals("-") ? -1 : 1) * Double.parseDouble(exercise[i + 2]));
                    temp1 = (exercise[i-2]+exercise[i-1]+exercise[i]+exercise[i+1]+exercise[i+2]).replaceAll("([+\\-*/^√])", "\\\\$1");
                    temp1 = k.replaceAll(temp1,(sum < 0 ? "":"+") + sum);
                    wayToSolution = wayToSolution + "<br> <font color='blue'>" + (exercise[i-2]+exercise[i-1]+exercise[i]+exercise[i+1]+exercise[i+2])  +"</font>" + " = <font color='green'>" + sum + "</font>";
                }else {
                    if (exercise[i+1].equals("-"))
                        throw new Exception(MessageType.UNDEFINED_OPERATION.toString());
                    sum = Math.sqrt(Double.parseDouble(exercise[i + 2]));
                    wayToSolution = wayToSolution + "<br> <font color='blue'>" + ((exercise[i]+exercise[i+1]+exercise[i+2]))  +"</font>" + " = <font color='green'>" + sum + "</font>";
                    temp1 = (exercise[i]+exercise[i+1]+exercise[i+2]).replaceAll("([+\\-*/^√])","\\\\$1");
                    temp1 = k.replaceAll(temp1,"+"+sum);
                }
                k = temp1;
                try {
                    exercise = order(temp1);
                }catch (Exception e){
                    throw new Exception(MessageType.Syntax_Error.toString());
                }
            }
        }
        return exercise;
    }
}
package com.haikarose.cman.tools;


public class UsefullyFunctions {

    public static String getPhone(String phone){

        final String code="+255";
        String returned="";
        if(phone.charAt(0)=='0'){
            returned=code.concat(phone.substring(1,phone.length()));
        }else if(phone.charAt(0)=='2' && phone.charAt(3)=='0'){
            returned=code.concat(phone.substring(4,phone.length()));
        }else if(phone.charAt(0)=='+' && phone.charAt(4)=='0'){
            returned=code.concat(phone.substring(5,phone.length()));
        }else if(phone.charAt(0)=='2'){
            returned="+".concat(phone);
        }else if(phone.charAt(0)=='+'){
            returned=phone;
        }
        return returned;
    }

    public static boolean isPhoneValid(String phone){

        if(phone.length()<10){
            return false;
        }

        if(phone.length()==10 &&  phone.charAt(0)=='0'){

            if(phone.substring(0,4).equalsIgnoreCase("0719")
                    ||phone.substring(0,4).equalsIgnoreCase("0714")
                    ||phone.substring(0,4).equalsIgnoreCase("0715")
                    ||phone.substring(0,4).equalsIgnoreCase("0717")
                    ||phone.substring(0,4).equalsIgnoreCase("0785")
                    ||phone.substring(0,4).equalsIgnoreCase("0789")
                    ||phone.substring(0,4).equalsIgnoreCase("0783")
                    ||phone.substring(0,4).equalsIgnoreCase("0687")
                    ||phone.substring(0,4).equalsIgnoreCase("0754")
                    ||phone.substring(0,4).equalsIgnoreCase("0757")
                    ||phone.substring(0,4).equalsIgnoreCase("0755")
                    ||phone.substring(0,4).equalsIgnoreCase("0673")
                    ||phone.substring(0,4).equalsIgnoreCase("0674")
                    ||phone.substring(0,4).equalsIgnoreCase("0624")
                    ||phone.substring(0,4).equalsIgnoreCase("0625")){

                try {

                    int number= Integer.parseInt(phone);
                }catch (Exception ex){
                    return false;
                }
                return true;
            }
        }

        if(phone.length()>14){
            return false;
        }

        if(phone.length()==13){

            if(phone.charAt(0)=='+'){

                if (phone.substring(1,4).equalsIgnoreCase("255")) {

                    if(phone.substring(4,7).equalsIgnoreCase("719")
                            ||phone.substring(4,7).equalsIgnoreCase("714")
                            ||phone.substring(4,7).equalsIgnoreCase("715")
                            ||phone.substring(4,7).equalsIgnoreCase("717")
                            ||phone.substring(4,7).equalsIgnoreCase("785")
                            ||phone.substring(4,7).equalsIgnoreCase("789")
                            ||phone.substring(4,7).equalsIgnoreCase("783")
                            ||phone.substring(4,7).equalsIgnoreCase("687")
                            ||phone.substring(4,7).equalsIgnoreCase("754")
                            ||phone.substring(4,7).equalsIgnoreCase("757")
                            ||phone.substring(4,7).equalsIgnoreCase("755")
                            ||phone.substring(4,7).equalsIgnoreCase("673")
                            ||phone.substring(4,7).equalsIgnoreCase("674")
                            ||phone.substring(4,7).equalsIgnoreCase("624")
                            ||phone.substring(4,7).equalsIgnoreCase("625")){

                        try {

                            int number= Integer.valueOf(phone.substring(1,4));
                            int number2= Integer.valueOf(phone.substring(4,phone.length()));


                        }catch (Exception ex){
                            System.out.println(ex);
                            return false;

                        }
                        return true;
                    }
                }
            }else if(phone.substring(0,3).equalsIgnoreCase("255")){

                if(phone.substring(3,4).equalsIgnoreCase("0")){
                    if(phone.substring(4,7).equalsIgnoreCase("719")
                            ||phone.substring(4,7).equalsIgnoreCase("714")
                            ||phone.substring(4,7).equalsIgnoreCase("715")
                            ||phone.substring(4,7).equalsIgnoreCase("717")
                            ||phone.substring(4,7).equalsIgnoreCase("785")
                            ||phone.substring(4,7).equalsIgnoreCase("789")
                            ||phone.substring(4,7).equalsIgnoreCase("783")
                            ||phone.substring(4,7).equalsIgnoreCase("687")
                            ||phone.substring(4,7).equalsIgnoreCase("754")
                            ||phone.substring(4,7).equalsIgnoreCase("757")
                            ||phone.substring(4,7).equalsIgnoreCase("755")
                            ||phone.substring(4,7).equalsIgnoreCase("673")
                            ||phone.substring(4,7).equalsIgnoreCase("674")
                            ||phone.substring(4,7).equalsIgnoreCase("624")
                            ||phone.substring(4,7).equalsIgnoreCase("625")){

                        try {

                            int number= Integer.valueOf(phone.substring(1,4));
                            int number2= Integer.valueOf(phone.substring(4,phone.length()));


                        }catch (Exception ex){
                            System.out.println(ex);
                            return false;

                        }
                        return true;
                    }
                }
            }
            return false;
        }

        if(phone.length()==14){

            if(phone.charAt(0)=='+'){

                if (phone.substring(1,4).equalsIgnoreCase("255")) {

                    if(phone.substring(4,8).equalsIgnoreCase("0719")
                            ||phone.substring(4,8).equalsIgnoreCase("0714")
                            ||phone.substring(4,8).equalsIgnoreCase("0715")
                            ||phone.substring(4,8).equalsIgnoreCase("0717")
                            ||phone.substring(4,8).equalsIgnoreCase("0785")
                            ||phone.substring(4,8).equalsIgnoreCase("0789")
                            ||phone.substring(4,8).equalsIgnoreCase("0783")
                            ||phone.substring(4,8).equalsIgnoreCase("0687")
                            ||phone.substring(4,8).equalsIgnoreCase("0754")
                            ||phone.substring(4,8).equalsIgnoreCase("0757")
                            ||phone.substring(4,8).equalsIgnoreCase("0755")
                            ||phone.substring(4,8).equalsIgnoreCase("0673")
                            ||phone.substring(4,8).equalsIgnoreCase("0674")
                            ||phone.substring(4,8).equalsIgnoreCase("0624")
                            ||phone.substring(4,8).equalsIgnoreCase("0625")){

                        try {

                            System.out.println(phone.substring(4,5));
                            if(!phone.substring(4,5).equalsIgnoreCase("0")){
                                return false;
                            }
                            int number= Integer.valueOf(phone.substring(1,4));
                            int number2= Integer.valueOf(phone.substring(5,phone.length()));

                        }catch (Exception ex){
                            return false;

                        }
                        return true;
                    }
                }
            }
            return false;
        }

        if(phone.length()==12){

            if(phone.substring(0,3).equalsIgnoreCase("255")){
                if(phone.substring(3,6).equalsIgnoreCase("719")
                        ||phone.substring(3,6).equalsIgnoreCase("714")
                        ||phone.substring(3,6).equalsIgnoreCase("715")
                        ||phone.substring(3,6).equalsIgnoreCase("717")
                        ||phone.substring(3,6).equalsIgnoreCase("785")
                        ||phone.substring(3,6).equalsIgnoreCase("789")
                        ||phone.substring(3,6).equalsIgnoreCase("783")
                        ||phone.substring(3,6).equalsIgnoreCase("687")
                        ||phone.substring(3,6).equalsIgnoreCase("754")
                        ||phone.substring(3,6).equalsIgnoreCase("757")
                        ||phone.substring(3,6).equalsIgnoreCase("755")
                        ||phone.substring(3,6).equalsIgnoreCase("673")
                        ||phone.substring(3,6).equalsIgnoreCase("674")
                        ||phone.substring(3,6).equalsIgnoreCase("624")
                        ||phone.substring(3,6).equalsIgnoreCase("625")){
                    try {

                        int number= Integer.valueOf(phone.substring(0,3));
                        int number2= Integer.valueOf(phone.substring(3,phone.length()));

                    }catch (Exception ex){
                        return false;
                    }
                    return true;
                }
            }
        }



        return false;
    }
}

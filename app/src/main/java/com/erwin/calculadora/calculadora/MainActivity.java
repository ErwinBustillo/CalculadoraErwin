package com.erwin.calculadora.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    float result=0;
    String op="";
    TextView resultado;
    EditText operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultado = (TextView) findViewById(R.id.textResultado);
        operation =(EditText) findViewById(R.id.editText);
    }

    public void onClickBoton7(View view) {
        op+=7+"";
        operation.setText(op);
    }

    public void onClickBoton8(View view) {
        op+=8+"";
        operation.setText(op);
    }

    public void onClickBoton9(View view) {
        op+=9+"";
        operation.setText(op);
    }

    public void onClickBoton4(View view) {
        op+=4+"";
        operation.setText(op);
    }

    public void onClickBoton5(View view) {
        op+=5+"";
        operation.setText(op);
    }

    public void onClickBoton6(View view) {
        op+=6+"";
        operation.setText(op);
    }

    public void onClickBoton1(View view) {
        op+=1+"";
        operation.setText(op);
    }

    public void onClickBoton2(View view) {
        op+=2+"";
        operation.setText(op);
    }

    public void onClickBoton3(View view) {
        op+=3+"";
        operation.setText(op);
    }

    public void onClickBotonPunto(View view) {
        op+=".";
        operation.setText(op);
    }

    public void onClickBotonCero(View view) {
        op+=0+"";
        operation.setText(op);
    }

    public void onClickBotonIgual(View view) {
        //Resolver aqui con la funcion eval
        if (op.equals("")){//SI se quiere escribir desde el Input inicial
            op=operation.getText().toString();
        }
        resultado.setText(eval(op)+"");
        op="";
        operation.setText(op);
    }

    public void onClickBotonDel(View view) {
        op=op.substring(0,op.length()-1);
        operation.setText(op);
    }

    public void onClickBotonDiv(View view) {
        op+="/";
        operation.setText(op);
    }

    public void onClickBotonMult(View view) {
        op+="*";
        operation.setText(op);
    }

    public void onClickBotonResta(View view) {
        op+="-";
        operation.setText(op);
    }

    public void onClickBotonSuma(View view) {
        op+="+";
        operation.setText(op);
    }

    // ESTA FUNCION SIRVE PARA EVALUAR EXPRESIONES ARITMETICAS SOBRE STRING REFERENCIA(https://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form)
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}

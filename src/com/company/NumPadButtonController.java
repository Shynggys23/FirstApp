package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ResourceBundle;

public class NumPadButtonController implements Initializable {

    Operations operations = new Operations();
    private BigDecimal result_B = new BigDecimal("0");
    private BigDecimal   arg1_B = new BigDecimal("0");
    private StringBuilder bufInputNumStr = new StringBuilder("");
    private StringBuilder printStr = new StringBuilder("");
    private StringBuilder bufstr = new StringBuilder("");
    private boolean opClickState = false;
    private Op opClickType;
    private boolean equalsSignBtnActive = false;
    private boolean isFuncActive = false;

    @FXML
    private TextField argAndOpOutput = new TextField();
    @FXML
    private TextField resultLabel = new TextField("0");
    @FXML
    public ScrollBar scrollBar = new ScrollBar();
    enum Op {
        additionOp,
        subtractionOp,
        divOp,
        multOp,
        sqr,
        sqrt,
        negate
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollBar.setVisible(false);
        scrollBar.setMin(0);
        scrollBar.setMax(50);
        scrollBar.setValue(argAndOpOutput.getText().length());
        scrollBar.setVisibleAmount(45);
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            argAndOpOutput.positionCaret(newValue.intValue());
        });
    }


    private void setTextForArgAndOpOutput(String s) {
        argAndOpOutput.setText(s);
        if (argAndOpOutput.getText().length() > 45) {
            scrollBar.setVisible(true);
            scrollBar.setMax(argAndOpOutput.getText().length());
            scrollBar.setValue(argAndOpOutput.getText().length());
            argAndOpOutput.positionCaret(argAndOpOutput.getText().length());
        }

    }
    private void setTextResultLabel(BigDecimal result) {
        if (String.valueOf(result).length() > 45) {
            resultLabel.setText(String.valueOf(result.round(MathContext.DECIMAL64)));
            resultLabel.positionCaret(0);
        } else {
            resultLabel.setText(result.toPlainString());
        }
    }
    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case NUMPAD1:
                numpudProc(1);
                break;
            case NUMPAD2:
                numpudProc(2);
                break;
            case NUMPAD3:
                numpudProc(3);
                break;
            case NUMPAD4:
                numpudProc(4);
                break;
            case NUMPAD5:
                numpudProc(5);
                break;
            case NUMPAD6:
                numpudProc(6);
                break;
            case NUMPAD7:
                numpudProc(7);
                break;
            case NUMPAD8:
                numpudProc(8);
                break;
            case NUMPAD9:
                numpudProc(9);
                break;
            case NUMPAD0:
                numpudProc(0);
                break;
            case DELETE:
                resetProc();
                break;
            case ADD:
                operProc("+", Op.additionOp);
                break;
            case SUBTRACT:
                operProc("-", Op.subtractionOp);
                break;
            case MULTIPLY:
                operProc("×", Op.multOp);
                break;
            case DIVIDE:
                operProc("÷", Op.divOp);
                break;
            case ENTER:
                equalsSignproc();
                break;
            case BACK_SPACE:
                backspaceProc();
                break;
            default:
        }

    }
    private void resetProc() {
        result_B = new BigDecimal("0");
        arg1_B = new BigDecimal("0");
        bufInputNumStr.delete(0, bufInputNumStr.length());
        printStr.delete(0, printStr.length());
        setTextForArgAndOpOutput("");
        resultLabel.setText("0");
        opClickState = false;
        opClickType = null;
        equalsSignBtnActive = false;
        isFuncActive = false;
        bufstr.delete(0, bufstr.length());
        scrollBar.setVisible(false);
        scrollBar.setMin(0);
        scrollBar.setMax(50);
        scrollBar.setValue(argAndOpOutput.getText().length());
        scrollBar.setVisibleAmount(45);
    }
    @FXML
    private void clickResetBtn(ActionEvent event) {
        resetProc();
    }
    @FXML
    private void clickNegateBtn(ActionEvent event) {
        arg1_B = arg1_B.multiply(new BigDecimal("-1"));
        setTextResultLabel(arg1_B);

    }
    @FXML
    private void clickBackspaceBtn(ActionEvent event) {
        backspaceProc();
    }
    private void backspaceProc() {
        if (bufInputNumStr.length() == 0) {

        } else {
            if (bufInputNumStr.length() != 1) {
                bufInputNumStr.deleteCharAt(bufInputNumStr.length() - 1);

                arg1_B = new BigDecimal(String.valueOf(bufInputNumStr));
            } else {
                bufInputNumStr.delete(0, bufInputNumStr.length());
                arg1_B = new BigDecimal("0");
            }
            setTextResultLabel(arg1_B);

        }
    }
    private BigDecimal getresultOpB(Op opClickType) {
        switch (opClickType) {
            case additionOp:
                result_B = operations.additionOp(result_B, arg1_B);
                break;
            case subtractionOp:
                result_B = operations.subtractionOp(result_B, arg1_B);
                break;
            case divOp:
                result_B = operations.divOp(result_B, arg1_B);
                break;
            case multOp:
                result_B = operations.multOp(result_B, arg1_B);
                break;
            default:
                result_B = new BigDecimal("0");
        }
        return result_B;
    }
    private void operProc(String opChar, Op opClickTypeP) {
        if (equalsSignBtnActive) {
            opClickState = false;
            opClickType = null;
            arg1_B = new BigDecimal(String.valueOf(result_B));
            equalsSignBtnActive = false;
        }
        if (opClickState) {
            if (opClickType != null && bufInputNumStr.length() == 0) {
                if (!isFuncActive) {
                    printStr.deleteCharAt(printStr.length() - 1);
                } else {
                    result_B = new BigDecimal(String.valueOf(arg1_B));
                }
                printStr.append(opChar);
                setTextForArgAndOpOutput(String.valueOf(printStr));
                opClickState = true;
                opClickType = opClickTypeP;
                isFuncActive = false;
            } else {
                result_B = getresultOpB(opClickType);
                if (isFuncActive) {
                    printStr.append(opChar);
                } else {
                    printStr.append(arg1_B.toPlainString() + opChar);
                }

                setTextForArgAndOpOutput(String.valueOf(printStr));
                setTextResultLabel(result_B);
                opClickState = true;
                opClickType = opClickTypeP;
                bufInputNumStr.delete(0, bufInputNumStr.length());
                arg1_B = new BigDecimal(String.valueOf(result_B));
                isFuncActive = false;
            }
        } else {
            if (result_B.compareTo(new BigDecimal("0")) == 0 && arg1_B.compareTo(new BigDecimal("0")) == 0) {
                printStr.append("0" + opChar);
                setTextForArgAndOpOutput(String.valueOf(printStr));
                setTextResultLabel(result_B);
                opClickState = true;
                opClickType = opClickTypeP;
            } else {
                if (opClickType != null) {
                    result_B = getresultOpB(opClickType);
                } else {
                    result_B = new BigDecimal(String.valueOf(arg1_B));
                }
                if (isFuncActive) {
                    printStr.append(opChar);
                } else {
                    printStr.append(arg1_B.toPlainString() + opChar);
                }

                setTextForArgAndOpOutput(String.valueOf(printStr));
                setTextResultLabel(result_B);
                opClickState = true;
                opClickType = opClickTypeP;
                bufInputNumStr.delete(0, bufInputNumStr.length());
                arg1_B = new BigDecimal(String.valueOf(result_B));
                isFuncActive = false;
            }
        }
        bufstr.delete(0, bufstr.length());
    }
    @FXML
    private void clickAddBtn(ActionEvent event) {
        operProc("+", Op.additionOp);
    }
    @FXML
    private void clickSubtractionOp() {
        operProc("-", Op.subtractionOp);
    }
    @FXML
    private void clickmultOp() {
        operProc("×", Op.multOp);
    }
    @FXML
    private void clickdivOp() {
        operProc("÷", Op.divOp);
    }
    @FXML
    private void clickSqrtBtn(ActionEvent event) {
        calcFuncProc("√", Op.sqrt);
    }
    @FXML
    private void clickSqrBtn(ActionEvent event) {
        calcFuncProc("sqr", Op.sqr);
    }
    private void calcFuncProc(String funcChar, Op funcType) {
        if (equalsSignBtnActive) {
            opClickState = false;
            opClickType = null;
            arg1_B = new BigDecimal(String.valueOf(result_B));
            equalsSignBtnActive = false;
        }
        if (!isFuncActive) {
            bufInputNumStr.delete(0, bufInputNumStr.length());
            printStr.append(funcChar + "(" + arg1_B.toPlainString() + ")");
            bufstr.append(funcChar + "(" + arg1_B.toPlainString() + ")");
        } else {
            printStr.delete(printStr.length() - bufstr.length(), printStr.length());
            bufstr.insert(0, funcChar + "(");
            bufstr.append(")");
            printStr.append(bufstr);
        }
        setTextForArgAndOpOutput(String.valueOf(printStr));
        switch (funcType) {
            case sqr:
                arg1_B = new BigDecimal(String.valueOf(arg1_B.multiply(arg1_B))).setScale(16, BigDecimal.ROUND_CEILING).stripTrailingZeros();
                break;
            case sqrt:
                arg1_B = new BigDecimal(String.valueOf(Math.sqrt(arg1_B.doubleValue()))).setScale(16, BigDecimal.ROUND_CEILING).stripTrailingZeros();
                break;
            default:
        }
        setTextResultLabel(arg1_B);
        isFuncActive = true;
    }
    private void numpudProc(int n) {
        isFuncActive = false;
        opClickState = false;
        if (arg1_B.compareTo(new BigDecimal("0")) == 0 && !opClickState) {
            arg1_B = new BigDecimal(String.valueOf(n));
            bufInputNumStr.append(n);
            setTextResultLabel(arg1_B);
        } else {
            if (printStr.indexOf("sqr") != -1 && opClickType == null) {
                bufInputNumStr.delete(0, bufInputNumStr.length());
                printStr.delete(0, printStr.length());
                setTextForArgAndOpOutput("");
            }
            arg1_B = new BigDecimal(String.valueOf(bufInputNumStr.append(n)));
            setTextResultLabel(arg1_B);
        }
    }
    private void equalsSignproc() {
        isFuncActive = false;
        bufstr.delete(0, bufstr.length());
        if (opClickType != null) {
            result_B = getresultOpB(opClickType);
            setTextResultLabel(result_B);
            equalsSignBtnActive = true;
            printStr.delete(0, printStr.length());
            setTextForArgAndOpOutput("");
            opClickState = false;
        }
        scrollBar.setVisible(false);
        scrollBar.setMin(0);
        scrollBar.setMax(50);
        scrollBar.setValue(argAndOpOutput.getText().length());
        scrollBar.setVisibleAmount(45);
    }
    @FXML
    private void equalsSignBtn(ActionEvent event) {
        equalsSignproc();
    }
    @FXML
    private void click1(ActionEvent event) {
        numpudProc(1);
    }
    @FXML
    private void click2(ActionEvent event) {
        numpudProc(2);
    }
    @FXML
    private void click3(ActionEvent event) {
        numpudProc(3);
    }
    @FXML
    private void click4(ActionEvent event) {
        numpudProc(4);
    }
    @FXML
    private void click5(ActionEvent event) {
        numpudProc(5);
    }
     @FXML
    private void click6(ActionEvent event) {
        numpudProc(6);
    }
    @FXML
    private void click7(ActionEvent event) {
        numpudProc(7);
    }
    @FXML
    private void click8(ActionEvent event) {
        numpudProc(8);
    }
    @FXML
    private void click9(ActionEvent event) {
        numpudProc(9);
    }
    @FXML
    private void click0(ActionEvent event) {
        numpudProc(0);
    }


}

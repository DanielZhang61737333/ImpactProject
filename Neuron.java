import java.util.ArrayList;

public class Neuron {
private float activationValue, weight;
private String neuronName;
private ArrayList<Neuron> outputs = new ArrayList<Neuron>();
private float[] inputs;
int inputCounter = 0, nInputs;

public Neuron(float activationValue, float weight, String neuronName, int nInputs){
    this.activationValue = activationValue;
    this.weight = weight;
    this.neuronName = neuronName;
    this.nInputs = nInputs;

    inputs = new float[nInputs];
}

public void connect(Neuron neuron){
    outputs.add(neuron);
}

public void input(float inputValue){
    inputs[inputCounter] = inputValue;
    inputCounter++;

    if(inputCounter == nInputs){
        fire();
    }
}

public void fire(){
    float sum = 0;
    for(int i = 0; i < nInputs; i++){
        sum+=inputs[i];
    }

    float signal = sum*weight;

    if(signal > activationValue){
        for(int i = 0; i < outputs.size(); i++){
            outputs.get(i).input(signal);
        }
    } else{
        for(int i = 0; i < outputs.size(); i++){
            outputs.get(i).input(0);
        }
    }
    System.out.println(neuronName + ":" + signal);
}
}

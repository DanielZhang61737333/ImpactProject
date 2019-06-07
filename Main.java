public class Main {

public static void main(String[] args) {
    Neuron inputNode = new Neuron(0,1,"InputNode",1);

    Neuron hiddenNode_One = new Neuron(0.5f,0.9f,"HiddenNode_One",1);
    Neuron hiddenNode_Two = new Neuron(0.5f,0.9f,"HiddenNode_Two",1);
    Neuron hiddenNode_Three = new Neuron(0.5f,0.9f,"HiddenNode_Three",1);

    Neuron outputNode = new Neuron(0,0.9f,"OutputNode",3);

    inputNode.connect(hiddenNode_One);
    inputNode.connect(hiddenNode_Two);
    inputNode.connect(hiddenNode_Three);

    hiddenNode_One.connect(outputNode);
    hiddenNode_Two.connect(outputNode);
    hiddenNode_Three.connect(outputNode);

    inputNode.input(1);
}

}

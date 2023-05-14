package com.crisis.crisisproject;

import org.springframework.stereotype.Service;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.util.Random;


@Service
public class DecisionTree {

    public String adviceFromDT(String contenuNotif) throws Exception {
        File file = new File("src/main/java/com/crisis/crisisproject/data.arff");
        DataSource source = new DataSource(file.getPath());
        Instances dataset = source.getDataSet();

        File testFile = new File("src/main/java/com/crisis/crisisproject/dataTest.arff");
        DataSource testSource = new DataSource(testFile.getPath());
        Instances testData = testSource.getDataSet();

        // Définir l'attribut de classe
        dataset.setClassIndex(dataset.numAttributes() - 1);
        testData.setClassIndex(testData.numAttributes()-1);
        // Créer un nouvel arbre de décision
        J48 tree = new J48();

        Remove remove = new Remove();
        String[] opts = new String[]{"-R","1"};
        remove.setOptions(opts);
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(remove);
        fc.setClassifier(tree);
        fc.buildClassifier(dataset);
        System.out.println(tree.toSummaryString());
        // Évaluer la précision du modèle sur un ensemble de test

        Evaluation eval = new Evaluation(testData);
        eval.crossValidateModel(fc, testData, 6, new Random(1));
        System.out.println(eval.toSummaryString());

        // Utiliser le modèle pour prédire le destinataire approprié pour de nouveaux messages
        Instance newMessage = new DenseInstance(dataset.numAttributes());
        Attribute msg =  dataset.attribute("message");
        System.out.println("msg "+msg.index());
        newMessage.setValue(msg,contenuNotif);
        newMessage.setDataset(dataset);
        double prediction = tree.classifyInstance(newMessage);
        System.out.println(prediction);
        return  dataset.classAttribute().value((int)prediction);

    }

    public static void main(String[] args) throws Exception {
        DecisionTree decisionTreeService = new DecisionTree();
        System.out.println(decisionTreeService.adviceFromDT("ascenseur est bloqué"));
    }

}

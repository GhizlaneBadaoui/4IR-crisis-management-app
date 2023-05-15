package com.crisis.crisisproject.services;

import org.springframework.stereotype.Service;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.util.Random;


@Service
public class DecisionTreeService {

    public String adviceFromDT(String contenuNotif) throws Exception {
        File file = new File("src/main/java/com/projet/crisis/Service/data.arff");
        System.out.println(file.getPath()+" ----> "+file.getAbsolutePath());
        DataSource source = new DataSource(file.getPath());
        Instances data = source.getDataSet();

        // Définir l'attribut de classe
        data.setClassIndex(data.numAttributes() - 1);
        // Créer un nouvel arbre de décision
        J48 tree = new J48();
        // Entraîner le modèle sur les données

        Remove remove = new Remove();
        String[] opts = new String[]{"-R","1"};
        remove.setOptions(opts);
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(remove);
        fc.setClassifier(tree);
        fc.buildClassifier(data);
        System.out.println(tree.toSummaryString());
        // Évaluer la précision du modèle sur un ensemble de test
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(fc, data, 3, new Random(1));
        System.out.println(eval.toSummaryString());

        // Utiliser le modèle pour prédire le destinataire approprié pour de nouveaux messages
        Instance newMessage = new DenseInstance(data.numAttributes());
        data.add(newMessage);
        newMessage.setDataset(data);
        newMessage.setValue(0,contenuNotif);
        // newMessage.setValue(1,"il y a un incendie au département info");
        double prediction = tree.classifyInstance(newMessage);
        System.out.println("Depuis la fonction adviceFromDT de la classe DecisionServiceTree , le contenu du message de l'argument d'entré est "+ contenuNotif);
        return  data.classAttribute().value((int)prediction);
        //return " le GEI";
    }

    public static void main(String[] args) throws Exception {
        DecisionTreeService decisionTreeService = new DecisionTreeService();
        System.out.println(decisionTreeService.adviceFromDT("gei"));
    }





}


package com.crisis.crisisproject.model;

import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.classifiers.meta.FilteredClassifier;
import java.io.File;
import java.util.Random;


@Service
public class DecisionTree {

    public String adviceFromDT(int numInstance) throws Exception {
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


        /***
         Remove remove = new Remove();
         String[] opts = new String[]{"-R","1"};
         remove.setOptions(opts);
         FilteredClassifier fc = new FilteredClassifier();
         fc.setFilter(remove);
         fc.setClassifier(tree);
         fc.buildClassifier(dataset);
         System.out.println(tree.toSummaryString());***/
        // Conversion de l'attribut "message" en attribut nominal
        StringToNominal filter = new StringToNominal();
        filter.setAttributeRange("1"); // Indice de l'attribut "message"
        filter.setInputFormat(dataset);
        Instances newData = Filter.useFilter(dataset, filter);
        System.out.println("dataset:"+newData);
        tree.buildClassifier(newData);

        // Évaluer la précision du modèle sur un ensemble de test

        Evaluation eval = new Evaluation(newData);
        eval.crossValidateModel(tree, newData, 6, new Random(1));
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
        System.out.println(tree.toString());
        // Utiliser le modèle pour prédire le destinataire approprié pour de nouveaux messages
        /***Instance newMessage = new DenseInstance(dataset.numAttributes());
         Attribute msg =  dataset.attribute("message");
         System.out.println("msg "+msg.index());
         newMessage.setValue(msg,contenuNotif);
         newMessage.setDataset(dataset);
         double prediction = tree.classifyInstance(newMessage);
         System.out.println(prediction);***/
        Instance newInstance = newData.instance(numInstance); // Remplacez 0 par l'indice de l'instance souhaitée
        System.out.println("nouvelle instance:"+newInstance);
        // Obtention de la prédiction pour la nouvelle instance
        double prediction = tree.classifyInstance(newInstance);

        return  dataset.classAttribute().value((int)prediction);

    }
    public void randomForest(String contenuNotif) throws Exception {
        File file = new File("src/main/java/com/crisis/crisisproject/data.arff");
        DataSource source = new DataSource(file.getPath());
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);

        System.out.println("dataset avant modif:"+dataset);

        // Convertir les attributs de type string en attributs nominaux
        StringToNominal filter = new StringToNominal();
        filter.setAttributeRange("1"); // Indice de l'attribut "message"
        filter.setInputFormat(dataset);
        dataset = Filter.useFilter(dataset, filter);
        System.out.println("dataset apres modif:"+dataset);
        // Créer un classifieur de forêt aléatoire avec 100 arbres
        Classifier randomForest = new RandomForest();
        randomForest.buildClassifier(dataset);
        //((RandomForest) randomForest).set;

        // Évaluer le classifieur en utilisant une validation croisée à 10 plis
        Evaluation evaluation = new Evaluation(dataset);
        evaluation.crossValidateModel(randomForest, dataset, 10, new Random(1));

        // Afficher les résultats
        //System.out.println(evaluation.toSummaryString());
        /***
         // Faire une prédiction sur un de nos données
         Instance newInstance = dataset.instance(numInstance);
         System.out.println("random forest:"+randomForest);
         double predictedClass = randomForest.classifyInstance(newInstance);
         String predictedClassName = dataset.classAttribute().value((int) predictedClass);
         System.out.println("Prediction sur l'instance:"+newInstance+" et la classe prédite est: " + predictedClassName);
         ***/
        //Faire une prediction sur une nouvelle instance
        Instance newMessage = new DenseInstance(dataset.numAttributes());
        Attribute msg =  dataset.attribute("message");
        System.out.println("msg "+msg.index());
        newMessage.setValue(msg,contenuNotif);

        newMessage.setDataset(dataset);
        double predictedClass2 = randomForest.classifyInstance(newMessage);
        String predictedClassName2 = dataset.classAttribute().value((int) predictedClass2);
        System.out.println("Prediction sur l'instance:"+newMessage+" et la classe prédite est: " + predictedClassName2);
    }
    public String StringToWordVector(String contenuNotif) throws Exception {
        File file = new File("src/main/java/com/crisis/crisisproject/model/data.arff");
        DataSource source = new DataSource(file.getPath());
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);
        //System.out.println("dataset avant modif:"+dataset);
        // Créer un transformateur StringToWordVector
        StringToWordVector filter = new StringToWordVector();
        filter.setAttributeIndices("first");// Indice de l'attribut "message"
        // Appliquer le transformateur aux données d'entraînement
        filter.setInputFormat(dataset);
        Instances filteredTrainingData = Filter.useFilter(dataset, filter);
        //System.out.println("dataset apres modif:"+filteredTrainingData);

        // Créer et entraîner le classifieur NaiveBayes
        Classifier classifier = new NaiveBayes();
        classifier.buildClassifier(filteredTrainingData);
        // Évaluer le classifieur en utilisant une validation croisée à 10 plis
        Evaluation evaluation = new Evaluation(filteredTrainingData);
        evaluation.crossValidateModel(classifier, filteredTrainingData, 10, new Random(1));

        // Afficher les résultats
        System.out.println(evaluation.toSummaryString());

        // Créer une nouvelle instance avec le message à prédire

        Instance newMessage = new DenseInstance(dataset.numAttributes());
        Attribute msg =  dataset.attribute("message");
        System.out.println("msg "+msg.index());
        newMessage.setValue(msg,contenuNotif);
        newMessage.setDataset(dataset);

        // Appliquer le transformateur à la nouvelle instance
        filter.input(newMessage);
        Instance filteredInstance = filter.output();
        //filteredInstance.setDataset(filteredTrainingData);
        // Prédire la classe de la nouvelle instance
        double prediction = classifier.classifyInstance(filteredInstance);

        // Récupérer la valeur prédite
        String predictedValue = filteredInstance.classAttribute().value((int) prediction);
        System.out.println("Prédiction : " + predictedValue);
        return predictedValue;
    }



    /***public static void main(String[] args) throws Exception {
     DecisionTree decisionTreeService = new DecisionTree();
     //System.out.println(decisionTreeService.adviceFromDT(5));
     //decisionTreeService.adviceFromDT("ascenseur est bloqué");
     //decisionTreeService.randomForest("un incendie au département ");
     decisionTreeService.StringToWordVector("je suis à l'amphi fourrier ");
     }***/

}

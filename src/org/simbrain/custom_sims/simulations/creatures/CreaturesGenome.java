package org.simbrain.custom_sims.simulations.creatures;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * TODO: Add javadocs :)
 */
public class CreaturesGenome {

	/** List of genes */
	List<CreaturesGene> geneList = new ArrayList();

	/**
	 * Gender defines whether a gene will be expressed in creatures of male, female,
	 * or either sex
	 */
	public static enum Gender {
		Both, Male, Female
	};

	/**
	 * LifeStage defines the life stage at which a gene will start to be expressed.
	 * Note that switching to another lifestage will not un-express the gene -
	 * nothing can do that as far as I know.
	 */
	public static enum LifeStage {
		Embryo, Child, Adolescent, Youth, Adult, Senile
	};

	/**
	 * GeneType defines the way the gene will be expressed. A BrainLobe gene will
	 * create a brain lobe, a ChemReceptor gene a chemical receptor, etc.
	 * ChemHalfLife and ChemConcentration genes are a bit special, in that only one
	 * of each is needed to define the half lives and initial concentrations of ALL
	 * chemicals.
	 */
	public static enum GeneType {
		BrainLobe, ChemReceptor, ChemEmitter, ChemReaction, ChemHalfLife, ChemConcentration, Stimulus, Instinct
	}

	/** Random number generator. Used in mutations and crossing genomes */
	Random rand = new Random();

	/**
	 * The probabilities of a type of mutation to occur. Might be fun to let the
	 * user configure this in the future.
	 */
	private double mutChance = 0.1;
	private double dupChance = 0.05;
	private double cutChance = 0.01;

	/** Constructor */
	public CreaturesGenome() {
		super();
	}

	/** Adds a gene to the gene list */
	public void addGene(CreaturesGene gene) {
		geneList.add(gene);
	}

	// TODO: Add method stubs or methods. mutate. cut. createrandomgenome
	// where possible document logic of method stubs

	/**
	 * Creates a new genome by crossing the one that owns this method with the one
	 * put in the argument
	 * 
	 * @param genome2
	 *            the genome to cross with
	 * @return a new genome
	 */
	public CreaturesGenome crossGenomes(CreaturesGenome genome2) {
		List<CreaturesGene> geneList2 = genome2.getGeneList();
		CreaturesGenome child = new CreaturesGenome();
		/*
		 * For each index of this genome's geneList and genome2's geneList that has a
		 * gene in it, use rand to pick which to add. Example: geneList and geneList2
		 * both have a gene in index 2. rand rolls and lands 0.1. Since 0.1 is less than
		 * the midpoint, it's geneList's gene that get copied into index 2 of this new
		 * genome
		 */
		/*
		 * Not sure what happens if one genome has a gene in an index that is blank in
		 * the other genome in the original games. Maybe we'll just copy the gene that
		 * is present without rolling rand?
		 */

		child.mutateCheck();
		return child;
	}

	/**
	 * A method for causing mutations by chance. Best to run this after all the
	 * genes you want have already been added.
	 */
	public void mutateCheck() {
		for (CreaturesGene gene : geneList) {
			double chance = rand.nextDouble();
			if (gene.mutable && chance < mutChance) {
				pointMutate(gene);
			} else if (gene.duplicatable && chance < dupChance) {
				dupMutate(gene);
			} else if (gene.cuttable && chance < cutChance) {
				cutMutate(gene);
			}
		}
	}

	/**
	 * A type of mutation. Changes one or several sequential bit(s) in the
	 * creature's gene.
	 * 
	 * @param gene
	 *            The gene to mutate
	 */
	public void pointMutate(CreaturesGene gene) {
		// Do stuff w/ the gene's bitset here
		// Maybe we should have this method just call a mutation method in the gene
		// itself?
	}

	/**
	 * A type of mutation. Adds a copy of the gene to the genome.
	 * 
	 * @param gene
	 *            The gene to duplicate
	 */
	public void dupMutate(CreaturesGene gene) {
		CreaturesGene dup = gene.deepCopy();
		addGene(dup);
	}

	/**
	 * A type of mutation. Cuts a gene from the genome
	 * 
	 * @param gene
	 *            The gene to remove
	 */
	public void cutMutate(CreaturesGene gene) {
		geneList.remove(gene);
	}

	/** Returns the geneList. Might be risky? */
	public List<CreaturesGene> getGeneList() {
		return geneList;
	}

	// temp testing main
	public static void main(String[] args) {
		CreaturesGenome genome = new CreaturesGenome();
		genome.addGene(new CreaturesGene("gene1", true, true, true, Gender.Both, LifeStage.Adolescent));
		genome.addGene(new CreaturesGene("gene2", true, true, true, Gender.Both, LifeStage.Adolescent));
		System.out.println(genome);
	}

	@Override
	public String toString() {
		String retString = "Geneome\n------\n";
		for (CreaturesGene gene : geneList) {
			retString += gene.toString() + "\n";
		}
		return retString;
	}

}

package randomreverser.device;

import kaptainwutax.seedutils.lcg.LCG;
import kaptainwutax.seedutils.lcg.rand.CombinedJRand;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.lcg.rand.Rand;
import randomreverser.call.LatticeCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class LCGReverserDevice<R extends Rand> {

	protected Lattice<R> lattice;

	public LCGReverserDevice(long modulus) {
		this.lattice = new Lattice<>(modulus);
	}

	public Lattice<R> getLattice() {
		return this.lattice;
	}

	public void setLattice(Lattice<R> lattice) {
		this.lattice = lattice;
	}

	public void addCall(LatticeCall<R> call) {
		this.lattice.addCall(call);
	}

	public void processCall(LatticeCall<R> call) {
		this.lattice.processCall(call);
	}

	public List<Long> findAllSeeds(Process process) {
		return this.streamSeeds(process).collect(Collectors.toCollection(ArrayList::new));
	}

	public abstract Stream<Long> streamSeeds(Process process);

	public enum Process {
		LATTICE_ONLY, BRUTEFORCE_ONLY, EVERYTHING
	}

}

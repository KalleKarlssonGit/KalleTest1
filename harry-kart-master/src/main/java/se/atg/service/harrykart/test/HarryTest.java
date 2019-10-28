// TODO move to test-folder and run
//@RunWith(SpringRunner.class)
//public class HarryTest{
//
//	@Autowired
//	HarryKartService harryKartService;
//
//	@Test
//	public void testTotalTime() {
//		BigInteger nrOfLoops = new BigInteger("2");
//
//		ParticipantType participantType = new ParticipantType();
//		participantType.setLane(new BigInteger("1"));
//		participantType.setName("theHorse");
//		participantType.setBaseSpeed(new BigInteger("10"));
//
//		List<LoopType> loopTypeList = new ArrayList<>();
//
//		/* Loop 1 start */
//		LoopType loopType = new LoopType();
//		loopType.setNumber(new BigInteger("1"));
//
//		List<LaneType> laneTypeList = new ArrayList<>();
//		LaneType laneType = new LaneType();
//		laneType.setNumber(new BigInteger("1"));
//		laneType.setValue(new BigInteger("3"));
//		laneTypeList.add(laneType);
//		laneType.setNumber(new BigInteger("2"));
//		laneType.setValue(new BigInteger("-111"));
//		laneTypeList.add(laneType);
//
//		loopType.setLane(laneTypeList);
//		/* Loop 1 end */
//
//		/* Loop 2 start */
//		loopType = new LoopType();
//		loopType.setNumber(new BigInteger("2"));
//
//		laneTypeList = new ArrayList<>();
//		laneType = new LaneType();
//		laneType.setNumber(new BigInteger("1"));
//		laneType.setValue(new BigInteger("0"));
//		laneTypeList.add(laneType);
//		laneType.setNumber(new BigInteger("2"));
//		laneType.setValue(new BigInteger("789"));
//		laneTypeList.add(laneType);
//
//		loopType.setLane(laneTypeList);
//		/* Loop 2 end */
//
//		loopTypeList.add(loopType);
//
//		//(BigInteger numberOfLoops, ParticipantType participantType, List<LoopType> loopTypeList) {
//		Double res = harryKartService.getHorseTotalTime(nrOfLoops, participantType, loopTypeList);
//		System.out.println("res:" + res);
//		Double shouldBe = new Double(253.8461538461);
//		assertTrue(shouldBe.equals(res));
//	}
//
//	@Test
//	public void testTotalWinners() {
//
//	}
//
//}

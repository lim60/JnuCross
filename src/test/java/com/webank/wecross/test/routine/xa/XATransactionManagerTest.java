// package com.webank.wecross.test.routine.xa;
//
// import com.webank.wecross.account.UniversalAccount;
// import com.webank.wecross.exception.WeCrossException;
// import com.webank.wecross.resource.Resource;
// import com.webank.wecross.routine.xa.XATransactionManager;
// import com.webank.wecross.routine.xa.XATransactionManager.ReduceCallback;
// import com.webank.wecross.stub.*;
// import com.webank.wecross.zone.Chain;
// import com.webank.wecross.zone.Zone;
// import com.webank.wecross.zone.ZoneManager;
// import java.util.HashSet;
// import java.util.Set;
// import org.junit.Assert;
// import org.junit.Test;
// import org.mockito.Mockito;
// import org.mockito.invocation.InvocationOnMock;
// import org.mockito.stubbing.Answer;
//
// public class XATransactionManagerTest {
//    @Test
//    public void testPrepare() throws Exception {
//        BlockManager blockManager = Mockito.mock(BlockManager.class);
//
//        Chain chain = Mockito.mock(Chain.class);
//        Mockito.when(chain.getBlockManager()).thenReturn(blockManager);
//
//        Zone zone = Mockito.mock(Zone.class);
//        Mockito.when(zone.getChain(Mockito.any(Path.class))).thenReturn(chain);
//
//        UniversalAccount ua = Mockito.mock(UniversalAccount.class);
//
//        Resource proxyResource = Mockito.mock(Resource.class);
//        proxyResource.setResourceInfo(new ResourceInfo());
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                TransactionRequest request = invocation.getArgument(0);
//                                UniversalAccount ua1 = invocation.getArgument(1);
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                Assert.assertEquals(ua1, ua);
//
//                                Assert.assertEquals("startTransaction", request.getMethod());
//                                Assert.assertEquals("0001", request.getArgs()[0]);
//
//                                Set<String> paths = new HashSet<String>();
//                                for (String path : request.getArgs()) {
//                                    paths.add(path);
//                                }
//                                if (request.getArgs()[1].startsWith("a.b")) {
//                                    Assert.assertTrue(paths.contains("a.b.c1"));
//                                    Assert.assertTrue(paths.contains("a.b.c2"));
//                                } else {
//                                    Assert.assertTrue(paths.contains("a.c.c1"));
//                                    Assert.assertTrue(paths.contains("a.c.c2"));
//                                }
//
//                                TransactionResponse transactionResponse = new
// TransactionResponse();
//                                transactionResponse.setErrorCode(0);
//                                transactionResponse.setMessage("");
//                                transactionResponse.setResult(new String[] {"0"});
//
//                                callback.onTransactionResponse(null, transactionResponse);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncSendTransaction(Mockito.any(), Mockito.any(), Mockito.any());
//
//        Mockito.when(proxyResource.getStubType()).thenReturn("test");
//
//        ZoneManager zoneManager = Mockito.mock(ZoneManager.class);
//        Mockito.when(zoneManager.getZone(Mockito.any(Path.class))).thenReturn(zone);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.b.WeCrossProxy")))
//                .thenReturn(proxyResource);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.c.WeCrossProxy")))
//                .thenReturn(proxyResource);
//
//        XATransactionManager xaTransactionManager = new XATransactionManager();
//        xaTransactionManager.setZoneManager(zoneManager);
//
//        String transactionID = "0001";
//
//        Set<Path> resources = new HashSet<Path>();
//        resources.add(Path.decode("a.b.c1"));
//        resources.add(Path.decode("a.b.c2"));
//        resources.add(Path.decode("a.c.c1"));
//        resources.add(Path.decode("a.c.c2"));
//
//        xaTransactionManager.asyncStartXATransaction(
//                transactionID,
//                ua,
//                resources,
//                new ReduceCallback() {
//                    @Override
//                    public void onResponse(WeCrossException e, int result) {
//                        Assert.assertNull(e);
//                        Assert.assertEquals(0, result);
//                    }
//                });
//
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                callback.onTransactionResponse(
//                                        new TransactionException(-1, ""), null);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncSendTransaction(Mockito.any(), Mockito.any(), Mockito.any());
//
//        xaTransactionManager.asyncStartXATransaction(
//                transactionID,
//                ua,
//                resources,
//                new ReduceCallback() {
//                    @Override
//                    public void onResponse(WeCrossException e, int result) {
//                        Assert.assertNotNull(e);
//                        Assert.assertEquals(-1, result);
//                    }
//                });
//    }
//
//    @Test
//    public void testCommit() throws Exception {
//        BlockManager blockManager = Mockito.mock(BlockManager.class);
//
//        Chain chain = Mockito.mock(Chain.class);
//        Mockito.when(chain.getBlockManager()).thenReturn(blockManager);
//
//        Zone zone = Mockito.mock(Zone.class);
//        Mockito.when(zone.getChain(Mockito.any(Path.class))).thenReturn(chain);
//
//        UniversalAccount ua = Mockito.mock(UniversalAccount.class);
//
//        Resource proxyResource = Mockito.mock(Resource.class);
//        proxyResource.setResourceInfo(new ResourceInfo());
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                TransactionRequest request = invocation.getArgument(0);
//                                UniversalAccount ua1 = invocation.getArgument(1);
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                Assert.assertEquals(ua1, ua);
//
//                                Assert.assertEquals("commitTransaction", request.getMethod());
//                                Assert.assertEquals("0001", request.getArgs()[0]);
//
//                                TransactionResponse transactionResponse = new
// TransactionResponse();
//                                transactionResponse.setErrorCode(0);
//                                transactionResponse.setMessage("");
//                                transactionResponse.setResult(new String[] {"0"});
//
//                                callback.onTransactionResponse(null, transactionResponse);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncSendTransaction(Mockito.any(), Mockito.any(), Mockito.any());
//        Mockito.when(proxyResource.getStubType()).thenReturn("test");
//
//        ZoneManager zoneManager = Mockito.mock(ZoneManager.class);
//        Mockito.when(zoneManager.getZone(Mockito.any(Path.class))).thenReturn(zone);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.b.WeCrossProxy")))
//                .thenReturn(proxyResource);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.c.WeCrossProxy")))
//                .thenReturn(proxyResource);
//
//        XATransactionManager xaTransactionManager = new XATransactionManager();
//        xaTransactionManager.setZoneManager(zoneManager);
//
//        String transactionID = "0001";
//
//        Set<Path> resources = new HashSet<Path>();
//        resources.add(Path.decode("a.b.c1"));
//        resources.add(Path.decode("a.b.c2"));
//        resources.add(Path.decode("a.c.c1"));
//        resources.add(Path.decode("a.c.c2"));
//
//        xaTransactionManager.asyncCommitXATransaction(
//                transactionID,
//                ua,
//                resources,
//                new ReduceCallback() {
//                    @Override
//                    public void onResponse(WeCrossException e, int result) {
//                        Assert.assertNull(e);
//                        Assert.assertEquals(0, result);
//                    }
//                });
//
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                callback.onTransactionResponse(
//                                        new TransactionException(-1, ""), null);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncSendTransaction(Mockito.any(), Mockito.any(), Mockito.any());
//
//        xaTransactionManager.asyncCommitXATransaction(
//                transactionID,
//                ua,
//                resources,
//                new ReduceCallback() {
//                    @Override
//                    public void onResponse(WeCrossException e, int result) {
//                        Assert.assertNotNull(e);
//                        Assert.assertEquals(-1, result);
//                    }
//                });
//    }
//
//    @Test
//    public void testRollback() throws Exception {
//        BlockManager blockManager = Mockito.mock(BlockManager.class);
//
//        Chain chain = Mockito.mock(Chain.class);
//        Mockito.when(chain.getBlockManager()).thenReturn(blockManager);
//
//        Zone zone = Mockito.mock(Zone.class);
//        Mockito.when(zone.getChain(Mockito.any(Path.class))).thenReturn(chain);
//
//        UniversalAccount ua = Mockito.mock(UniversalAccount.class);
//
//        Resource proxyResource = Mockito.mock(Resource.class);
//        proxyResource.setResourceInfo(new ResourceInfo());
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                TransactionRequest request = invocation.getArgument(0);
//                                UniversalAccount ua1 = invocation.getArgument(1);
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                Assert.assertEquals(ua1, ua);
//
//                                Assert.assertEquals("rollbackTransaction", request.getMethod());
//                                Assert.assertEquals("0001", request.getArgs()[0]);
//
//                                TransactionResponse transactionResponse = new
// TransactionResponse();
//                                transactionResponse.setErrorCode(0);
//                                transactionResponse.setMessage("");
//                                transactionResponse.setResult(new String[] {"0"});
//
//                                callback.onTransactionResponse(null, transactionResponse);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncSendTransaction(Mockito.any(), Mockito.any(), Mockito.any());
//        Mockito.when(proxyResource.getStubType()).thenReturn("test");
//
//        ZoneManager zoneManager = Mockito.mock(ZoneManager.class);
//        Mockito.when(zoneManager.getZone(Mockito.any(Path.class))).thenReturn(zone);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.b.WeCrossProxy")))
//                .thenReturn(proxyResource);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.c.WeCrossProxy")))
//                .thenReturn(proxyResource);
//
//        XATransactionManager xaTransactionManager = new XATransactionManager();
//        xaTransactionManager.setZoneManager(zoneManager);
//
//        String transactionID = "0001";
//
//        Set<Path> resources = new HashSet<Path>();
//        resources.add(Path.decode("a.b.c1"));
//        resources.add(Path.decode("a.b.c2"));
//        resources.add(Path.decode("a.c.c1"));
//        resources.add(Path.decode("a.c.c2"));
//
//        xaTransactionManager.asyncRollbackXATransaction(
//                transactionID,
//                ua,
//                resources,
//                new ReduceCallback() {
//                    @Override
//                    public void onResponse(WeCrossException e, int result) {
//                        Assert.assertNull(e);
//                        Assert.assertEquals(0, result);
//                    }
//                });
//
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                callback.onTransactionResponse(
//                                        new TransactionException(-1, ""), null);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncSendTransaction(Mockito.any(), Mockito.any(), Mockito.any());
//
//        xaTransactionManager.asyncRollbackXATransaction(
//                transactionID,
//                ua,
//                resources,
//                new ReduceCallback() {
//                    @Override
//                    public void onResponse(WeCrossException e, int result) {
//                        Assert.assertNotNull(e);
//                        Assert.assertEquals(-1, result);
//                    }
//                });
//    }
//
//    @Test
//    public void testGetTransactionInfo() throws Exception {
//        BlockManager blockManager = Mockito.mock(BlockManager.class);
//
//        Chain chain = Mockito.mock(Chain.class);
//        Mockito.when(chain.getBlockManager()).thenReturn(blockManager);
//
//        Zone zone = Mockito.mock(Zone.class);
//        Mockito.when(zone.getChain(Mockito.any(Path.class))).thenReturn(chain);
//
//        UniversalAccount ua = Mockito.mock(UniversalAccount.class);
//
//        Resource proxyResource = Mockito.mock(Resource.class);
//        proxyResource.setResourceInfo(new ResourceInfo());
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                TransactionRequest request = invocation.getArgument(0);
//                                UniversalAccount ua1 = invocation.getArgument(1);
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                Assert.assertEquals(ua1, ua);
//
//                                Assert.assertEquals("getTransactionInfo", request.getMethod());
//                                Assert.assertEquals("0001", request.getArgs()[0]);
//
//                                TransactionResponse transactionResponse = new
// TransactionResponse();
//                                transactionResponse.setErrorCode(0);
//                                transactionResponse.setMessage("");
//                                transactionResponse.setResult(
//                                        new String[] {
//                                            ""
//                                                    + "{\n"
//                                                    + "    	\"transactionID\": \"1\",\n"
//                                                    + "    	\"status\": 1,\n"
//                                                    + "    	\"startTimestamp\": \"123\",\n"
//                                                    + "    	\"commitTimestamp\": \"456\",\n"
//                                                    + "    	\"rollbackTimestamp\": \"789\",\n"
//                                                    + "    	\"transactionSteps\": [{\n"
//                                                    + "            	\"seq\": 0,\n"
//                                                    + "    			\"xuperchain.xuperchain.contract\": \"0x12\",\n"
//                                                    + "    			\"path\": \"a.b.c\",\n"
//                                                    + "    			\"timestamp\": \"123\",\n"
//                                                    + "    			\"func\": \"test1(string)\",\n"
//                                                    + "    			\"args\": \"aaa\"\n"
//                                                    + "    		},\n"
//                                                    + "    		{\n"
//                                                    + "    		    \"seq\": 1,\n"
//                                                    + "    			\"xuperchain.xuperchain.contract\": \"0x12\",\n"
//                                                    + "    			\"path\": \"a.b.c\",\n"
//                                                    + "    			\"timestamp\": \"123\",\n"
//                                                    + "    			\"func\": \"test2(string)\",\n"
//                                                    + "    			\"args\": \"bbb\"\n"
//                                                    + "    		}\n"
//                                                    + "    	]\n"
//                                                    + "    }"
//                                        });
//
//                                callback.onTransactionResponse(null, transactionResponse);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncCall(Mockito.any(), Mockito.any(), Mockito.any());
//        Mockito.when(proxyResource.getStubType()).thenReturn("test");
//
//        ZoneManager zoneManager = Mockito.mock(ZoneManager.class);
//        Mockito.when(zoneManager.getZone(Mockito.any(Path.class))).thenReturn(zone);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.b.WeCrossProxy")))
//                .thenReturn(proxyResource);
//        Mockito.when(zoneManager.fetchResource(Path.decode("a.c.WeCrossProxy")))
//                .thenReturn(proxyResource);
//
//        XATransactionManager xaTransactionManager = new XATransactionManager();
//        xaTransactionManager.setZoneManager(zoneManager);
//
//        String transactionID = "0001";
//
//        Set<Path> resources = new HashSet<Path>();
//        resources.add(Path.decode("a.b"));
//        resources.add(Path.decode("a.c"));
//
//        xaTransactionManager.asyncGetXATransaction(
//                transactionID,
//                ua,
//                resources,
//                (e, transactionInfo) -> {
//                    Assert.assertNull(e);
//                    Assert.assertEquals("1", transactionInfo.getTransactionID());
//                    // Assert.assertEquals(expected, actual);
//
//                    // Assert.assertEquals(5, transactionInfo.getPaths());
//                });
//
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                callback.onTransactionResponse(
//                                        new TransactionException(-1, ""), null);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncCall(Mockito.any(), Mockito.any(), Mockito.any());
//
//        xaTransactionManager.asyncGetXATransaction(
//                transactionID,
//                ua,
//                resources,
//                (e, transactionInfo) -> {
//                    Assert.assertNotNull(e);
//                });
//
//        Mockito.doAnswer(
//                        new Answer<Object>() {
//                            @Override
//                            public Object answer(InvocationOnMock invocation) throws Throwable {
//                                Resource.Callback callback = invocation.getArgument(2);
//
//                                TransactionResponse transactionResponse = new
// TransactionResponse();
//                                transactionResponse.setErrorCode(0);
//                                transactionResponse.setMessage("");
//                                transactionResponse.setResult(new String[] {"wrong json here"});
//
//                                callback.onTransactionResponse(null, transactionResponse);
//
//                                return null;
//                            }
//                        })
//                .when(proxyResource)
//                .asyncCall(Mockito.any(), Mockito.any(), Mockito.any());
//
//        xaTransactionManager.asyncGetXATransaction(
//                transactionID,
//                ua,
//                resources,
//                (e, transactionInfo) -> {
//                    Assert.assertNotNull(e);
//                    // Assert.assertEquals("Decode transactionInfo json error", e.getMessage());
//                });
//    }
// }

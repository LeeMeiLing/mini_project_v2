package sg.edu.nus.iss.server.models;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class HospitalCredentials extends Contract {
    public static final String BINARY = "608060405234801562000010575f80fd5b506040516200121c3803806200121c833981016040819052620000339162000086565b5f8054336001600160a01b031991821617909155600180549091166001600160a01b038416179055600262000069828262000200565b505050620002c8565b634e487b7160e01b5f52604160045260245ffd5b5f806040838503121562000098575f80fd5b82516001600160a01b0381168114620000af575f80fd5b602084810151919350906001600160401b0380821115620000ce575f80fd5b818601915086601f830112620000e2575f80fd5b815181811115620000f757620000f762000072565b604051601f8201601f19908116603f0116810190838211818310171562000122576200012262000072565b8160405282815289868487010111156200013a575f80fd5b5f93505b828410156200015d57848401860151818501870152928501926200013e565b5f8684830101528096505050505050509250929050565b600181811c908216806200018957607f821691505b602082108103620001a857634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115620001fb575f81815260208120601f850160051c81016020861015620001d65750805b601f850160051c820191505b81811015620001f757828155600101620001e2565b5050505b505050565b81516001600160401b038111156200021c576200021c62000072565b62000234816200022d845462000174565b84620001ae565b602080601f8311600181146200026a575f8415620002525750858301515b5f19600386901b1c1916600185901b178555620001f7565b5f85815260208120601f198616915b828110156200029a5788860151825594840194600190910190840162000279565b5085821015620002b857878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b610f4680620002d65f395ff3fe608060405260043610610110575f3560e01c80637d952fb71161009d578063bc64a91a11610062578063bc64a91a146102ca578063c04637111461033f578063c0ffaf8c14610354578063e83ddcea14610367578063edefe76614610397575f80fd5b80637d952fb7146102245780638c4b773d146102425780639328960214610261578063a240967714610275578063aa2fedde14610294575f80fd5b806329899d79116100e357806329899d79146101855780632de40ce3146101a65780634a4b674a146101cf578063673ea086146101ee5780636b87d24c14610203575f80fd5b806302e4eb9a146101145780630d55e1b1146101465780630edd2ffc1461015c5780631e59a49814610171575b5f80fd5b34801561011f575f80fd5b5061013361012e366004610a94565b6103b6565b6040519081526020015b60405180910390f35b348015610151575f80fd5b5061015a61047a565b005b348015610167575f80fd5b5061013360075481565b34801561017c575f80fd5b50600554610133565b348015610190575f80fd5b506101996104d8565b60405161013d9190610b69565b3480156101b1575f80fd5b506004546101bf9060ff1681565b604051901515815260200161013d565b3480156101da575f80fd5b5061015a6101e9366004610c14565b6105e3565b3480156101f9575f80fd5b5061013360035481565b34801561020e575f80fd5b506102176105fe565b60405161013d9190610c6e565b34801561022f575f80fd5b506004546101bf90610100900460ff1681565b34801561024d575f80fd5b5061015a61025c366004610c14565b61068a565b34801561026c575f80fd5b5061015a6106b3565b348015610280575f80fd5b5061015a61028f366004610c87565b6106d8565b34801561029f575f80fd5b505f546102b2906001600160a01b031681565b6040516001600160a01b03909116815260200161013d565b3480156102d5575f80fd5b506102e96102e4366004610c14565b6106f6565b6040805160ff9a8b168152988a1660208a01529689169688019690965293871660608701529186166080860152851660a085015290931660c083015260e08201929092529015156101008201526101200161013d565b34801561034a575f80fd5b5061013360065481565b61015a610362366004610ca0565b61076a565b348015610372575f80fd5b50610386610381366004610c14565b610982565b60405161013d959493929190610d21565b3480156103a2575f80fd5b506001546102b2906001600160a01b031681565b600880546001810182555f9182526003027ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee38101868155907ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee40161041a8682610de9565b5060028101805471ffffffffffffffffffffffffffffffffff00191661010060ff87160271ffffffffffffffffffffffffffffffff000019161762010000608086901c0217905560085461047090600190610eb9565b9695505050505050565b6001546001600160a01b03163314610490575f80fd5b6005545f906104a190600190610eb9565b90505f600582815481106104b7576104b7610ed2565b5f9182526020909120600260039092020101805460ff191660011790555050565b60408051610120810182525f80825260208201819052918101829052606081018290526080810182905260a0810182905260c0810182905260e081018290526101008101919091526005545f9061053190600190610eb9565b90506005818154811061054657610546610ed2565b5f91825260209182902060408051610120810182526003909302909101805460ff80821685526101008083048216968601969096526201000082048116938501939093526301000000810483166060850152640100000000810483166080850152650100000000008104831660a0850152600160301b9004821660c0840152600181015460e0840152600201541615159181019190915292915050565b6001546001600160a01b031633146105f9575f80fd5b600755565b6002805461060b90610d63565b80601f016020809104026020016040519081016040528092919081815260200182805461063790610d63565b80156106825780601f1061065957610100808354040283529160200191610682565b820191905f5260205f20905b81548152906001019060200180831161066557829003601f168201915b505050505081565b5f546001600160a01b0316331461069f575f80fd5b5f600882815481106104b7576104b7610ed2565b6001546001600160a01b031633146106c9575f80fd5b6004805460ff19166001179055565b6001546001600160a01b031633146106ee575f80fd5b60ff16600355565b60058181548110610705575f80fd5b5f91825260209091206003909102018054600182015460029092015460ff80831694506101008304811693620100008404821693630100000081048316936401000000008204841693650100000000008304811693600160301b909304811692911689565b5f546001600160a01b0316331461077f575f80fd5b6006545f90156107b1576003546107999062015180610ee6565b6006546107a69190610efd565b4211156107b1575060015b8015610800576007546107c5906001610ee6565b3410610800576001546040515f916001600160a01b0316903480156108fc029184818181858888f19350505050905080156107fe575f91505b505b801561085d5760405162461bcd60e51b815260206004820152602260248201527f506c65617365207061792070656e616c747920666f72206c6174652075706461604482015261746560f01b606482015260840160405180910390fd5b50600580546001810182555f919091526003027f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db08101805460ff938416600160301b0266ff00000000000019958516650100000000000265ff000000000019978616640100000000029790971665ffff000000001998861663010000000263ff000000199a871662010000029a909a1663ffff0000199b87166101000261ffff1990941696909c1695909517919091179890981698909817959095179390931692909217171691909117909155427f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db182018190557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db2909101805460ff19169055600655565b60088181548110610991575f80fd5b5f91825260209091206003909102018054600182018054919350906109b590610d63565b80601f01602080910402602001604051908101604052809291908181526020018280546109e190610d63565b8015610a2c5780601f10610a0357610100808354040283529160200191610a2c565b820191905f5260205f20905b815481529060010190602001808311610a0f57829003601f168201915b5050506002909301549192505060ff8082169161010081049091169062010000900460801b85565b634e487b7160e01b5f52604160045260245ffd5b803560ff81168114610a78575f80fd5b919050565b80356001600160801b031981168114610a78575f80fd5b5f805f8060808587031215610aa7575f80fd5b84359350602085013567ffffffffffffffff80821115610ac5575f80fd5b818701915087601f830112610ad8575f80fd5b813581811115610aea57610aea610a54565b604051601f8201601f19908116603f01168101908382118183101715610b1257610b12610a54565b816040528281528a6020848701011115610b2a575f80fd5b826020860160208301375f602084830101528097505050505050610b5060408601610a68565b9150610b5e60608601610a7d565b905092959194509250565b5f6101208201905060ff835116825260ff60208401511660208301526040830151610b99604084018260ff169052565b506060830151610bae606084018260ff169052565b506080830151610bc3608084018260ff169052565b5060a0830151610bd860a084018260ff169052565b5060c0830151610bed60c084018260ff169052565b5060e083015160e083015261010080840151610c0c8285018215159052565b505092915050565b5f60208284031215610c24575f80fd5b5035919050565b5f81518084525f5b81811015610c4f57602081850181015186830182015201610c33565b505f602082860101526020601f19601f83011685010191505092915050565b602081525f610c806020830184610c2b565b9392505050565b5f60208284031215610c97575f80fd5b610c8082610a68565b5f805f805f805f60e0888a031215610cb6575f80fd5b610cbf88610a68565b9650610ccd60208901610a68565b9550610cdb60408901610a68565b9450610ce960608901610a68565b9350610cf760808901610a68565b9250610d0560a08901610a68565b9150610d1360c08901610a68565b905092959891949750929550565b85815260a060208201525f610d3960a0830187610c2b565b94151560408301525060ff9290921660608301526001600160801b03191660809091015292915050565b600181811c90821680610d7757607f821691505b602082108103610d9557634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610de4575f81815260208120601f850160051c81016020861015610dc15750805b601f850160051c820191505b81811015610de057828155600101610dcd565b5050505b505050565b815167ffffffffffffffff811115610e0357610e03610a54565b610e1781610e118454610d63565b84610d9b565b602080601f831160018114610e4a575f8415610e335750858301515b5f19600386901b1c1916600185901b178555610de0565b5f85815260208120601f198616915b82811015610e7857888601518255948401946001909101908401610e59565b5085821015610e9557878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610ecc57610ecc610ea5565b92915050565b634e487b7160e01b5f52603260045260245ffd5b8082028115828204841417610ecc57610ecc610ea5565b80820180821115610ecc57610ecc610ea556fea26469706673582212202b640f50070dbd5b66f6dd6a44d03af875a5f51f78b93dd09c1701ec2996c37664736f6c63430008140033";

    public static final String FUNC_MOH = "MOH";

    public static final String FUNC_ADDREVIEW = "addReview";

    public static final String FUNC_GETLATESTSTAT = "getLatestStat";

    public static final String FUNC_GETSTATISTICSSIZE = "getStatisticsSize";

    public static final String FUNC_HOSPITAL = "hospital";

    public static final String FUNC_JCIACCREDITED = "jciAccredited";

    public static final String FUNC_LASTUPDATE = "lastUpdate";

    public static final String FUNC_LICENSE = "license";

    public static final String FUNC_PENALTY = "penalty";

    public static final String FUNC_REGISTERED = "registered";

    public static final String FUNC_REVIEWS = "reviews";

    public static final String FUNC_SETPENALTY = "setPenalty";

    public static final String FUNC_SETUPDATEFREQUENCY = "setUpdateFrequency";

    public static final String FUNC_STATISTICS = "statistics";

    public static final String FUNC_UPDATEFREQUENCY = "updateFrequency";

    public static final String FUNC_UPDATESTATISTIC = "updateStatistic";

    public static final String FUNC_VERIFYLICENSE = "verifyLicense";

    public static final String FUNC_VERIFYPATIENT = "verifyPatient";

    public static final String FUNC_VERIFYSTATISTIC = "verifyStatistic";

    @Deprecated
    protected HospitalCredentials(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HospitalCredentials(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HospitalCredentials(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HospitalCredentials(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> MOH() {
        final Function function = new Function(FUNC_MOH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addReview(BigInteger _reviewId, String _patientNRIC, BigInteger _overallRating, byte[] _hashedMessage) {
        final Function function = new Function(
                FUNC_ADDREVIEW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_reviewId), 
                new org.web3j.abi.datatypes.Utf8String(_patientNRIC), 
                new org.web3j.abi.datatypes.generated.Uint8(_overallRating), 
                new org.web3j.abi.datatypes.generated.Bytes16(_hashedMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Statistic> getLatestStat() {
        final Function function = new Function(FUNC_GETLATESTSTAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Statistic>() {}));
        return executeRemoteCallSingleValueReturn(function, Statistic.class);
    }

    public RemoteFunctionCall<BigInteger> getStatisticsSize() {
        final Function function = new Function(FUNC_GETSTATISTICSSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> hospital() {
        final Function function = new Function(FUNC_HOSPITAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> jciAccredited() {
        final Function function = new Function(FUNC_JCIACCREDITED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> lastUpdate() {
        final Function function = new Function(FUNC_LASTUPDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> license() {
        final Function function = new Function(FUNC_LICENSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> penalty() {
        final Function function = new Function(FUNC_PENALTY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> registered() {
        final Function function = new Function(FUNC_REGISTERED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>> reviews(BigInteger param0) {
        final Function function = new Function(FUNC_REVIEWS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint8>() {}, new TypeReference<Bytes16>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>>(function,
                new Callable<Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>>() {
                    @Override
                    public Tuple5<BigInteger, String, Boolean, BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, Boolean, BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setPenalty(BigInteger _wei) {
        final Function function = new Function(
                FUNC_SETPENALTY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_wei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUpdateFrequency(BigInteger _days) {
        final Function function = new Function(
                FUNC_SETUPDATEFREQUENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_days)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> statistics(BigInteger param0) {
        final Function function = new Function(FUNC_STATISTICS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (Boolean) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> updateFrequency() {
        final Function function = new Function(FUNC_UPDATEFREQUENCY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateStatistic(BigInteger _mortality, BigInteger _patientSafety, BigInteger _readmission, BigInteger _patientExperience, BigInteger _effectiveness, BigInteger _timeliness, BigInteger _medicalImagingEfficiency, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_UPDATESTATISTIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_mortality), 
                new org.web3j.abi.datatypes.generated.Uint8(_patientSafety), 
                new org.web3j.abi.datatypes.generated.Uint8(_readmission), 
                new org.web3j.abi.datatypes.generated.Uint8(_patientExperience), 
                new org.web3j.abi.datatypes.generated.Uint8(_effectiveness), 
                new org.web3j.abi.datatypes.generated.Uint8(_timeliness), 
                new org.web3j.abi.datatypes.generated.Uint8(_medicalImagingEfficiency)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyLicense() {
        final Function function = new Function(
                FUNC_VERIFYLICENSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyPatient(BigInteger _index) {
        final Function function = new Function(
                FUNC_VERIFYPATIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verifyStatistic() {
        final Function function = new Function(
                FUNC_VERIFYSTATISTIC, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HospitalCredentials load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HospitalCredentials(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HospitalCredentials load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HospitalCredentials(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HospitalCredentials load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HospitalCredentials(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HospitalCredentials load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HospitalCredentials(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HospitalCredentials> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _MOH, String _licenese) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _MOH), 
                new org.web3j.abi.datatypes.Utf8String(_licenese)));
        return deployRemoteCall(HospitalCredentials.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class Statistic extends StaticStruct {
        public BigInteger mortality;

        public BigInteger patientSafety;

        public BigInteger readmission;

        public BigInteger patientExperience;

        public BigInteger effectiveness;

        public BigInteger timeliness;

        public BigInteger medicalImagingEfficiency;

        public BigInteger timestamp;

        public Boolean verified;

        public Statistic(BigInteger mortality, BigInteger patientSafety, BigInteger readmission, BigInteger patientExperience, BigInteger effectiveness, BigInteger timeliness, BigInteger medicalImagingEfficiency, BigInteger timestamp, Boolean verified) {
            super(new org.web3j.abi.datatypes.generated.Uint8(mortality), 
                    new org.web3j.abi.datatypes.generated.Uint8(patientSafety), 
                    new org.web3j.abi.datatypes.generated.Uint8(readmission), 
                    new org.web3j.abi.datatypes.generated.Uint8(patientExperience), 
                    new org.web3j.abi.datatypes.generated.Uint8(effectiveness), 
                    new org.web3j.abi.datatypes.generated.Uint8(timeliness), 
                    new org.web3j.abi.datatypes.generated.Uint8(medicalImagingEfficiency), 
                    new org.web3j.abi.datatypes.generated.Uint256(timestamp), 
                    new org.web3j.abi.datatypes.Bool(verified));
            this.mortality = mortality;
            this.patientSafety = patientSafety;
            this.readmission = readmission;
            this.patientExperience = patientExperience;
            this.effectiveness = effectiveness;
            this.timeliness = timeliness;
            this.medicalImagingEfficiency = medicalImagingEfficiency;
            this.timestamp = timestamp;
            this.verified = verified;
        }

        public Statistic(Uint8 mortality, Uint8 patientSafety, Uint8 readmission, Uint8 patientExperience, Uint8 effectiveness, Uint8 timeliness, Uint8 medicalImagingEfficiency, Uint256 timestamp, Bool verified) {
            super(mortality, patientSafety, readmission, patientExperience, effectiveness, timeliness, medicalImagingEfficiency, timestamp, verified);
            this.mortality = mortality.getValue();
            this.patientSafety = patientSafety.getValue();
            this.readmission = readmission.getValue();
            this.patientExperience = patientExperience.getValue();
            this.effectiveness = effectiveness.getValue();
            this.timeliness = timeliness.getValue();
            this.medicalImagingEfficiency = medicalImagingEfficiency.getValue();
            this.timestamp = timestamp.getValue();
            this.verified = verified.getValue();
        }
    }
}

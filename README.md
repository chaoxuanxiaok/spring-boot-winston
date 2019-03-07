# spring-boot-winston
按照本说明，走近spring-cloud和spring-boot

     * SpringCloud将现下非常流行的技术整合到一起，实现诸如以下功能：
     *      配置管理、服务发现、智能路由、负载均衡、熔断器、控制总线、集群状态....
     *
     *   netflix：（仅列举部分）
     *      Eureka：注册中心
     *      Zuul：服务网关
     *      Ribbon：负载均衡
     *      Feign：服务调用
     *      Hystrix：熔断器
     *
     *  SpringCloud不是一个组件，是许多组件的集合，所以版本命名较特殊，是以大写字母为首的单词组成。
     *
     *  学习过程
     *      1.springboot-demo       一些springboot基础知识                       day01 笔记
     *      2.http-demo             http远程调用以及数据如何传输的                day02 笔记
     *      3.user-service-demo     服务提供者                                   day02 笔记
     *      4.user-consumer-demo    服务调用方                                   day02 笔记
     *      5.Eureka-server-demo    服务注册中心                                 day02 笔记
     *                 3.1 user-service-demo   注册服务到EurekaServer                     day02 笔记
     *      *          4.1 user-consumer-demo  到EurekaServer订阅服务                     day02 笔记
     *      *          5.1 Eureka-server1-demo、Eureka-server2-demo   搭建Eureka集群          day02 笔记
     *      *          3.2 user-service1-demo    copy一份user-service-demo，作为两个实例启动  day02 笔记
     *      *          4.2 user-consumer-demo     利用ribbon的算法去调用服务             day02 笔记
     *      *          4.3 user-consumer-demo     修改ribbon负载均衡策略                 day02 笔记
     *      *          4.4 user-consumer-demo     增强RestTemplate重试能力               day02 笔记
     *      *          4.5 user-consumer-demo     增加熔断机制                           day03 笔记
     *      *          3.2 user-service-demo      增加休眠时间，触发熔断机制              day03 笔记
     *      *          4.6 user-consumer-demo     通过Feign客户端实现调用                day03 笔记
     *      6.Zuul-demo             服务网关                                     day03 笔记
     *
     * Eureka 服务注册中心-------可以是一个集群
     *      场景：
     *          user-service对外提供服务，需要暴露地址；
     *          consumer调用服务，需要记录服务提供者的地址。
     *          服务少还好，如果服务十几数十个呢？若人为管理地址，开发、测试、上线都极为麻烦
     *      用途：
     *          Eureka负责管理、记录服务提供者的信息；
     *          服务调用者无需自己寻找服务，只需把需求告知Eureka，它会帮你自动匹配何时的服务
     *          同时，服务提供方与Eureka通过“心跳”机制进行监控，服务出现问题会被Eureka从服务列表剔除
     *          即---------服务自动注册、发现、状态监控
     *      原理：
     *          Eureka：服务注册中心，对外暴露自己的地址
     *          提供方：启动后向Eureka注册自己的信息（地址、什么服务）
     *              （可以是spring-boot，也可以是其他技术实现，只要对外提供rest风格服务就行）
     *          消费方：向Eureka订阅服务，Eureka将对应服务的所有提供者地址列表发送给消费者，并定期更新
     *
     *          心跳（续约）：提供方定期通过http方式向Eureka刷新自己的状态
     *
     *  1.1 如何搭建集群，形成高可用的Eureka中心
     *      （1）数据同步
     *          多个EurekaServer之间会互相注册为服务
     *          服务提供者注册信息到某个EurekaServer节点时，该节点会把服务信息同步给集群中每个EurekaServer节点
     *          调用方访问到集群中的任意EurekaServer节点，都能获取完整的服务列表信息
     *      （2）动手搭建高可用的EurekaServer
     *          1）新建两个EurekaServer的demo，端口分别是10087/10088
     *          2）其中一个demo配置其他Eureka节点的地址，另一个demo反之
     *          3）idea中一个应用不能启动两次，我们需要重新配置一个启动器
     *              Edit Configurations--> + --> spring-boot -->配置启动类
     *          4）无论是提供方还是消费方，在注册服务的时候，service-url需要变化，将集群内的所有节点配置
     *
     *  1.2 服务提供者   与Eureka交互原理
     *      （1）服务注册
     *          user-service-demo启动时，检测配置文件中的 eureka.client.register-with-eureka=true
     *              如果没有，默认为true；如果有，且为true
     *                  携带元数据信息，向EurekaServer发起一个Rest请求
     *                      EurekaServer保存这些数据，双层map结构：
     *                          第一层Map的key是服务名称；第二层Map的key是服务的实例id
     *      （2）服务续约
     *          user-service-demo注册完成后，会维持一个心跳（定时向EurekaServer发送Rest请求）
     *              在配置文件中做相应的控制：
     *                  eureka.instance.lease-expiration-duration-in-seconds=90     服务失效时间，默认90s
     *                  eureka.instance.lease-renewal-interval-in-seconds=30        服务续约间隔，默认30s
     *              上面两个值，生产环境不要修改，保持默认，开发阶段可以适当调小
     *                  每隔30s服务会向注册中心发送一次心跳
     *                  若超过90s没有发送心跳，EurekaServer认为该服务宕机，从服务列表中移除
     *      （3）服务信息
     *          Instance currently registered with Eureka中
     *              status列中：
     *                  up(n):代表启动了n个示例
     *                  up(n)-之后的是  示例的名称，也就是 instance-id（区分同一服务不同实例的唯一标准，不能重复）
     *                      instance-id的默认格式：${hostname} + ${spring.application.name} + ${server.port}
     *                          如果修改，可以在配置文件中：
     *                              eureka.instance.instance-id=${spring.application.name}:${server.port}
     *
     *  1.3 服务消费者   与Eureka交互原理
     *      （1）获取服务列表
     *          user-consumer-demo启动时，检测配置文件中 eureka.client.fetch-registry=true
     *              如果没有，默认为true；如果有，且为true
     *                  从EurekaServer服务列表只读备份，缓存在本地，且每隔30秒重新获取并更新数据
     *                  eureka.client.registry-fetch-interval-seconds=5
     *                  生产上无需修改，开发环境中可以适当调小
     *  1.4 失效剔除和自我保护
     *      （1）失效剔除
     *          服务提供方可能因为内存溢出、网络故障等原因导致服务无法正常工作
     *          EurekaServer会开启定时任务，每隔60秒对所有失效服务（超过90秒未响应）进行剔除
     *              配置文件对剔除的间隔时间做控制
     *                  eureka.server.eviction-interval-timer-in-ms=60
     *                  生产环境不要需要，开发中可以调整到10s
     *      （2）自我保护
     *          关停一个服务时，会在Eureka面板看到警告，这是触发Eureka的自我保护机制。
     *              一个服务未按时进行心跳续约时，Eureka会统计 最近15分钟心跳失败的服务实例的比例是否超过了85%。
     *              生产环境下网络延迟等原因，心跳失败实例的比例很有可能超标，但服务可能没有宕机，把服务剔除列表并不妥当，Eureka就会把当前实例的注册信息保护起来，不予剔除。
     *
     *              生产环境下这很有效，保证了大多数服务依然可用，
     *              但给开发带来不便，可以通过下面配置关闭自我保护
     *                  eureka.server.enable-self-preservation: false       # 关闭自我保护模式（缺省为打开）
     *                  eureka.server.eviction-interval-timer-in-ms: 1000   # 扫描失效服务的间隔时间（缺省为60*1000ms）
     *
     *  负载均衡    Ribbon
     *      （1）Ribbon是什么？
     *          Netflix发布的负载均衡器，有助于控制http和tcp客户端的行为
     *          为Ribbon配置服务提供者的地址列表后，可基于某种负载均衡算法，自动帮助消费者请求
     *          默认有 轮询、随机等算法，也可以自定义
     *      （2）使用场景：
     *          在user-consumer-demo中，通过DiscoveryClient来获取服务实例信息，然后获取ip和端口来访问。
     *          但实际环境中，会开启很多个user-service-demo服务的集群。
     *          此时我们获取的服务列表中就会有多个，选择访问哪一个，就需要编写负载均衡算法，在多个实例列表中进行选择。
     *              解决方案：Eureka集成了负载均衡组件：Ribbon，简单修改代码即可使用。
     *      （3）使用：
     *          1）启动两个user-service-demo的实例
     *          2）在消费方的启动类的RestTemplate的配置方法上添加`@LoadBalanced`注解：
     *          3）修改调用方式，不再手动获取ip和端口，而是直接通过服务名称调用：
     *                  String baseUrl = "http://user-service-demo/user/";
     *
     *    1.1 源码分析
     *          （1）LoadBalancerInterceptor帮我们根据service名称，获取到了服务实例的ip和端口
     *          （2）通过执行LoadBalancerClient的execute方法
     *              获取一个负载均衡器ILoadBalancer
     *              根据负载均衡器的算法在server列表中选择server
     *          （3）源码追踪：
     *                  @LoadBalanced追踪到 package org.springframework.cloud.client.loadbalancer;
     *                  继续追踪到 LoadBalancerInterceptor
     *                  从拦截器中注意到 负载均衡客户端有个 execute方法
     *                  往下追踪到 RibbonLoadBalancerClient的 choose方法
     *                  继续追踪RibbonLoadBalancerClient的getServer方法
     *                  ILoadBalancer的 chooseServer方法
     *                  追踪到BaseLoadBalancer的chooseServer方法
     *                  最关键的到了 rule.choose(key);
     *                      这个rule  this.rule = DEFAULT_RULE;
     *                      而 IRule DEFAULT_RULE = new RoundRobinRule();   也就是说 默认是轮询
     *                      这里就是定义 负载均衡的规则接口
     *
     *    1.2   负载均衡策略
     *          （1）默认是简单的轮询
     *          （2）如何修改策略
     *                  通过上面的源码追踪 接口IRule 是定义规则的，查看有哪些实现类
     *                  在配置文件中：
     *                      {服务名称}.ribbon.NFLoadBalancerRuleClassName=全类名
     *                  例如：
     *                      user-service-demo.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
     *
     *    1.3   重试机制
     *          Eureka强调CAP原则中的AP-----  可用性和可靠性
     *          Zookeeper强调CAP原则中的CP--  一致性和可靠性
     *
     *          说的啥玩意儿？
     *              Eureka为了实现更高的服务可用性，牺牲了一定的一致性
     *              极端情况下， 宁愿接收故障实例，也不放弃健康实例，即自我保护机制
     *
     *           问题：
     *              调用不正常的服务会失败，可能导致其他服务无法正常工作
     *              举例：开启user-service-demo的8081和8083两个端口
     *                     此时consumer调用，正常
     *                     关掉8083端口，由于服务剔除的延迟，consumer不会立即得到最新服务列表
     *                     此时consumer调用报错 Whitelabel Error Page，但8081的服务其实正常的
     *           解决：
     *              使用Spring Retry增强RestTemplate的重试能力
     *                  ----一次服务调用失败，不会立即抛出异常，而是再次重试另一个服务
     *                  配置文件中：
     *                      spring.cloud.loadbalancer.retry.enabled=true             # 开启Spring Cloud的重试功能
     *                      user-service-demo.ribbon.ConnectTimeout=250              # Ribbon的连接超时时间
     *                      user-service-demo.ribbon.ReadTimeout=1000                # Ribbon的数据读取超时时间
     *                      user-service-demo.ribbon.OkToRetryOnAllOperations=true   # 是否对所有操作都进行重试
     *                      user-service-demo.ribbon.MaxAutoRetriesNextServer=1      # 切换实例的重试次数
     *                      user-service-demo.ribbon.MaxAutoRetries=1                # 对当前实例的重试次数
     *                  引入spring-retry依赖
     *  Hystrix 熔断器
     *      Netflix开源的 延迟和容错库---隔离访问 远程服务、第三方库，防止出现级联失败
     *
     *      why 需要熔断器？
     *          复杂分布式结构通常都具有很多依赖
     *          当一个应用高度耦合其他服务时，非常危险且容易导致失败
     *          这种失败很容易伤害服务调用者，最后导致一个接一个的连续错误
     *          整个应用就处在被拖垮的风险中，最终失去控制
     *
     *          系统高峰期时，大量对微服务的调用可能会堵塞远程服务器的线程池
     *          如果这个线程池没有和主应用服务器的线程池隔离
     *          可能导致整个服务器挂机
     *
     *      熔断器是怎么工作的？
     *          类似于电路熔断，服务调用方可以自己进行判断
     *          当某些服务反应慢或者存在大量超时的情况，能主动熔断，防止整个系统被拖垮
     *          区别于电路熔断，Hystrix能弹性容错，情况好转可以自动重连
     *          断路时后续请求直接拒绝，一段时间后允许部分请求，调用成功则回到闭合状态，否则继续断开
     *
     *          有服务出现异常时，直接进行失败回滚，服务降级处理
     *          虽然拒绝用户访问，但是返回一个友好提示，而不是直接简单粗暴的报错
     *          系统特别繁忙时，一些次要服务暂时中断，一切资源优先让给主要服务，确保主要服务的畅通
     *
     *  Feign   声明式、模板化的HTTP客户端
     *
     *      把Rest的请求隐藏，伪装成类似SpringMVC的controller一样
     *         实现：创建一个接口，在接口上添加上一些注解
     *
     *      集成了Ribbon
     *
     *      集成了Hystrix
     *
     *      支持对请求和响应进行GZIP压缩，减少通信过程中的性能损耗
     *
     *     Feign客户端的日志级别需要额外指定
     *
     *  Zuul网关
     *      前情回顾：
     *          Eureka                  实现服务注册中心以及服务注册与发现
     *          Ribbon或Feign           实现服务的消费及负载均衡
     *          Spring Cloud Config     实现应用多环境的外部化配置以及版本管理
     *          Hystrix                 熔断机制避免微服务架构中个别服务出现异常引起的故障蔓延
     *
     *      为什么需要Zuul网关？
     *          在之前的架构中，服务集群内有多个ServiceA、多个ServiceB，还有Open Service
     *          ServiceA和ServiceB都会在EurekaServer注册与订阅
     *          但OpenService是对外的服务，通过均衡负载公开到服务调用方
     *          对外服务直接暴露服务地址的不足：----出于对开放服务的安全性考虑，需要实现对服务访问的权限控制
     *              破坏服务无状态的特点
     *              无法直接复用既有接口
     *          服务网关应运而生，将权限控制从服务单元中抽离出去
     *          服务网关具备 服务路由、均衡负载、权限控制等功能
     *
     *      什么是Zuul网关？
     *          Netflix开源的 微服务网关，能与Eureka、Ribbon、Hystrix配合使用
     *          核心是实现以下功能的一系列过滤器：
     *              身份认证与安全：识别每个资源的验证要求，拒绝与要求不符的请求
     *              审查与监控：     边缘位置追踪有意义的数据和统计结果
     *              动态路由：       动态将请求路由到不同的后端集群
     *              压力测试：       逐渐增加 指向集群 的流量，了解性能
     *              负载分配：       每种负载类型分配对应容量， 弃用超出限定值的请求
     *              静态响应处理：   边缘位置直接建立部分响应， 避免转发到内部集群
     *              多区域弹性：     跨越AWS Region进行请求路由， 实现ELB使用的多样化， 让系统边缘贴近系统使用者
     *          Zuul默认使用Apache Http Client客户端，Spring Cloud进行整合与增强，
     *          也可以使用RestClient或者okhttp3.OKHttpClient，需要在配置文件开启
     *
     *          Zuul作为服务的统一入口，
     *          无论是PC还是移动端，或者服务内部调用，所有请求都会经过Zuul，
     *          由Zuul网关来实现 鉴权、动态路由等操作。
     *
     *      zuul相关知识点：
     *          （1）如果一个服务就一个实例 -----------映射路径与实际地址
     *          （2）如果一个服务有多个实例 -----------映射路径与服务名称，借助Eureka
     *          （3）路由配置的简化写法    ------------ zuul.routes.<serviceId>=<path>
     *          （4）默认路由规则       ---------------默认情况下，一切服务的映射路径就是服务名本身
     *          （5）路由前缀
     *          （6）过滤器      ------------ ZuulFilter是过滤器的顶级父类
     *          （7）自定义过滤器   -----继承ZuuleFilter
     *          （8）负载均衡和熔断  -----Zuul集成了Ribbon和Hystrix，需要自己手动修改默认值
     *

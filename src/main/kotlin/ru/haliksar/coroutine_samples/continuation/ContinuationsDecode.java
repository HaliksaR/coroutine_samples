package ru.haliksar.coroutine_samples.continuation;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 4, 0},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u001a\u0011\u0010\u0000\u001a\u00020\u0001H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0002\u001a\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0019\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0001H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\b"},
        d2 = {"compressImage", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "main", "", "sendToServer", "image", "([BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "coroutine_samples"}
)
public final class ContinuationsDecodeKt {
    @Nullable
    public static final Object compressImage(@NotNull Continuation $completion) {
        Object $continuation;
        label20:
        {
            if ($completion instanceof <undefinedtype >){
            $continuation = ( < undefinedtype >)$completion;
            if (((( < undefinedtype >) $continuation).label & Integer.MIN_VALUE) !=0){
                (( < undefinedtype >) $continuation).label -= Integer.MIN_VALUE;
                break label20;
            }
        }

            $continuation = new ContinuationImpl($completion) {
                // $FF: synthetic field
                Object result;
                int label;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return ContinuationsDecodeKt.compressImage(this);
                }
            };
        }

        Object $result = (( < undefinedtype >) $continuation).result;
        Object var3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ((( < undefinedtype >) $continuation).label){
            case 0:
                ResultKt.throwOnFailure($result);
                (( < undefinedtype >) $continuation).label = 1;
                if (DelayKt.delay(2000L, (Continuation) $continuation) == var3) {
                    return var3;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        return new byte[]{1, 0, 0, 0, 0, 1, 1, 0};
    }

    @Nullable
    public static final Object sendToServer(@NotNull byte[] image, @NotNull Continuation $completion) {
        Object $continuation;
        label20:
        {
            if ($completion instanceof <undefinedtype >){
            $continuation = ( < undefinedtype >)$completion;
            if (((( < undefinedtype >) $continuation).label & Integer.MIN_VALUE) !=0){
                (( < undefinedtype >) $continuation).label -= Integer.MIN_VALUE;
                break label20;
            }
        }

            $continuation = new ContinuationImpl($completion) {
                // $FF: synthetic field
                Object result;
                int label;
                Object L$0;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return ContinuationsDecodeKt.sendToServer(null, this);
                }
            };
        }

        Object $result = (( < undefinedtype >) $continuation).result;
        Object var6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ((( < undefinedtype >) $continuation).label){
            case 0:
                ResultKt.throwOnFailure($result);
                (( < undefinedtype >) $continuation).L$0 = image;
                (( < undefinedtype >) $continuation).label = 1;
                if (DelayKt.delay(5000L, (Continuation) $continuation) == var6) {
                    return var6;
                }
                break;
            case 1:
                image = (byte[]) (( < undefinedtype >) $continuation).L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        int var2 = image.length;
        boolean var3 = false;
        System.out.println(var2);
        return Unit.INSTANCE;
    }

    public static final void main() {
        BuildersKt.runBlocking$default((CoroutineContext) null, (Function2) (new Function2((Continuation) null) {
            Object L$0;
            Object L$1;
            int label;
            private CoroutineScope p$;

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                label17:
                {
                    Object var6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    Object var10000;
                    CoroutineScope $this$runBlocking;
                    byte[] image;
                    switch (this.label) {
                        case 0:
                            ResultKt.throwOnFailure($result);
                            $this$runBlocking = this.p$;
                            String var7 = "Start";
                            boolean var4 = false;
                            System.out.println(var7);
                            this.L$0 = $this$runBlocking;
                            this.label = 1;
                            var10000 = ContinuationsDecodeKt.compressImage(this);
                            if (var10000 == var6) {
                                return var6;
                            }
                            break;
                        case 1:
                            $this$runBlocking = (CoroutineScope) this.L$0;
                            ResultKt.throwOnFailure($result);
                            var10000 = $result;
                            break;
                        case 2:
                            image = (byte[]) this.L$1;
                            $this$runBlocking = (CoroutineScope) this.L$0;
                            ResultKt.throwOnFailure($result);
                            break label17;
                        default:
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }

                    image = (byte[]) var10000;
                    this.L$0 = $this$runBlocking;
                    this.L$1 = image;
                    this.label = 2;
                    if (ContinuationsDecodeKt.sendToServer(image, this) == var6) {
                        return var6;
                    }
                }

                String var8 = "End";
                boolean var5 = false;
                System.out.println(var8);
                return Unit.INSTANCE;
            }

            @NotNull
            public final Continuation create(@Nullable Object value, @NotNull Continuation completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                Function2 var3 = new <anonymous constructor > (completion);
                var3.p$ = (CoroutineScope) value;
                return var3;
            }

            public final Object invoke(Object var1, Object var2) {
                return (( < undefinedtype >) this.create(var1, (Continuation) var2)).invokeSuspend(Unit.INSTANCE);
            }
        }), 1, (Object) null);
    }

    // $FF: synthetic method
    public static void main(String[] var0) {
        main();
    }
}

package service;

import dto.product.request.ComputerRequestDto;
import dto.product.request.ElectronicsRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.request.SmartphoneRequestDto;
import dto.product.response.ComputerResponseDto;
import dto.product.response.ElectronicsResponseDto;
import dto.product.response.ProductResponseDto;
import dto.product.response.SmartphoneResponseDto;
import exception.ProductNotFoundException;
import exception.ProductTypeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.impl.ComputerServiceImpl;
import service.impl.ElectronicsServiceImpl;
import service.impl.ProductFacadeServiceImpl;
import service.impl.SmartphoneServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFacadeServiceTest {

    @Mock
    private ComputerServiceImpl computerService;

    @Mock
    private ElectronicsServiceImpl electronicsService;

    @Mock
    private SmartphoneServiceImpl smartphoneService;

    @Mock
    private ComputerResponseDto compResponseDto;

    @Mock
    private ElectronicsResponseDto electronicsResponseDto;

    @Mock
    private SmartphoneResponseDto smartphoneResponseDto;

    private ProductFacadeServiceImpl productFacadeService;

    @BeforeEach
    void setUp() {
        productFacadeService = new ProductFacadeServiceImpl(
                List.of(
                        computerService,
                        electronicsService,
                        smartphoneService
                )
        );
    }

    @Test
    void shouldCreateComputerProduct() {
        ComputerRequestDto dto = mock(ComputerRequestDto.class);

        when(computerService.isInstance(dto)).thenReturn(true);
        when(computerService.create(dto, 1L)).thenReturn(compResponseDto);

        ProductResponseDto result = productFacadeService.createProduct(dto, 1L);

        assertEquals(compResponseDto, result);

        verify(computerService).create(dto, 1L);
        verify(electronicsService, never()).create(any(), anyLong());
        verify(smartphoneService, never()).create(any(), anyLong());
    }

    @Test
    void shouldCreateElectronicsProduct() {
        ElectronicsRequestDto dto = mock(ElectronicsRequestDto.class);

        when(computerService.isInstance(dto)).thenReturn(false);
        when(electronicsService.isInstance(dto)).thenReturn(true);
        when(electronicsService.create(dto, 1L)).thenReturn(electronicsResponseDto);

        ProductResponseDto result = productFacadeService.createProduct(dto, 1L);

        assertEquals(electronicsResponseDto, result);

        verify(electronicsService).create(dto, 1L);
    }

    @Test
    void shouldCreateSmartphoneProduct() {
        SmartphoneRequestDto dto = mock(SmartphoneRequestDto.class);

        when(computerService.isInstance(dto)).thenReturn(false);
        when(electronicsService.isInstance(dto)).thenReturn(false);
        when(smartphoneService.isInstance(dto)).thenReturn(true);
        when(smartphoneService.create(dto, 1L)).thenReturn(smartphoneResponseDto);

        ProductResponseDto result = productFacadeService.createProduct(dto, 1L);

        assertEquals(smartphoneResponseDto, result);

        verify(smartphoneService).create(dto, 1L);
    }

    @Test
    void shouldThrowExceptionWhenProductTypeNotFound() {
        ProductRequestDto dto = mock(ProductRequestDto.class);

        when(computerService.isInstance(dto)).thenReturn(false);
        when(electronicsService.isInstance(dto)).thenReturn(false);
        when(smartphoneService.isInstance(dto)).thenReturn(false);

        assertThrows(
                ProductTypeNotFoundException.class,
                () -> productFacadeService.createProduct(dto, 1L)
        );
    }

    @Test
    void shouldRemoveProduct() {
        when(computerService.exist(1L)).thenReturn(true);
        when(computerService.remove(1L, 10L)).thenReturn(compResponseDto);

        ProductResponseDto result =
                productFacadeService.removeProduct(1L, 10L);

        assertEquals(compResponseDto, result);
        verify(computerService).remove(1L, 10L);
    }

    @Test
    void shouldThrowExceptionWhenRemovingNotExistingProduct() {
        when(computerService.exist(1L)).thenReturn(false);
        when(electronicsService.exist(1L)).thenReturn(false);
        when(smartphoneService.exist(1L)).thenReturn(false);

        assertThrows(
                ProductNotFoundException.class,
                () -> productFacadeService.removeProduct(1L, 10L)
        );
    }

    @Test
    void shouldUpdateProductPrice() {
        BigDecimal price = BigDecimal.valueOf(1000);

        when(computerService.exist(1L)).thenReturn(true);
        when(computerService.updatePrice(1L, 10L, price))
                .thenReturn(compResponseDto);

        ProductResponseDto result =
                productFacadeService.updateProductPrice(1L, 10L, price);

        assertEquals(compResponseDto, result);
        verify(computerService).updatePrice(1L, 10L, price);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPriceOfNotExistingProduct() {
        when(computerService.exist(1L)).thenReturn(false);
        when(electronicsService.exist(1L)).thenReturn(false);
        when(smartphoneService.exist(1L)).thenReturn(false);

        assertThrows(
                ProductNotFoundException.class,
                () -> productFacadeService.updateProductPrice(
                        1L,
                        10L,
                        BigDecimal.TEN
                )
        );
    }

    @Test
    void shouldUpdateProductQuantity() {
        when(computerService.exist(1L)).thenReturn(true);
        when(computerService.updateQuantity(1L, 10L, 5))
                .thenReturn(compResponseDto);

        ProductResponseDto result =
                productFacadeService.updateProductQuantity(1L, 10L, 5);

        assertEquals(compResponseDto, result);
        verify(computerService).updateQuantity(1L, 10L, 5);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingQuantityOfNotExistingProduct() {
        when(computerService.exist(1L)).thenReturn(false);
        when(electronicsService.exist(1L)).thenReturn(false);
        when(smartphoneService.exist(1L)).thenReturn(false);

        assertThrows(
                ProductNotFoundException.class,
                () -> productFacadeService.updateProductQuantity(1L, 10L, 5)
        );
    }

    @Test
    void shouldReturnAllProductsSortedById() {
        ComputerResponseDto product1 = mock(ComputerResponseDto.class);
        ElectronicsResponseDto product2 = mock(ElectronicsResponseDto.class);
        SmartphoneResponseDto product3 = mock(SmartphoneResponseDto.class);

        when(product1.getId()).thenReturn(3L);
        when(product2.getId()).thenReturn(1L);
        when(product3.getId()).thenReturn(2L);

        when(computerService.getAll()).thenReturn(List.of(product1));
        when(electronicsService.getAll()).thenReturn(List.of(product2));
        when(smartphoneService.getAll()).thenReturn(List.of(product3));

        List<ProductResponseDto> result = productFacadeService.getAllProducts();

        assertEquals(3, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(3L, result.get(2).getId());
    }
}